package com.dydzik.command;

import com.dydzik.entity.Account;
import com.dydzik.entity.Bank;
import com.dydzik.entity.Transaction;
import com.dydzik.repository.AccountRepository;
import com.dydzik.repository.BankRepository;
import com.dydzik.repository.ClientRepository;
import com.dydzik.repository.TransactionRepository;

import java.sql.SQLException;
import java.util.Date;
import java.util.Random;

public class TransferFromClientToClientCommand implements Command {
    private final ClientRepository clientRepository;
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final BankRepository bankRepository;

    public TransferFromClientToClientCommand(ClientRepository clientRepository,
                                             TransactionRepository transactionRepository,
                                             AccountRepository accountRepository,
                                             BankRepository bankRepository) {
        this.clientRepository = clientRepository;
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.bankRepository = bankRepository;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 5) {
            System.out.println("Insufficient arguments. Usage: client transfer <fromID> <toID> <fromBankId> <toBankId> <amount>");
            return;
        }

        int clientFromId = Integer.parseInt(args[0]);
        int clientToId = Integer.parseInt(args[1]);
        int fromBankId = Integer.parseInt(args[2]);
        int toBankId = Integer.parseInt(args[3]);
        double amount = Double.parseDouble(args[4]);

        Account accountFrom;
        Account accountTo;
        try {
            accountFrom = accountRepository.findByClientIdAndBankId(clientFromId, fromBankId);
            accountTo = accountRepository.findByClientIdAndBankId(clientToId, toBankId);
        } catch (SQLException e) {
            System.out.println("Account was not found");
            throw new RuntimeException(e);
        }

        if (accountFrom == null || accountTo == null) {
            System.out.println("Account(s) with the given client ID is(are) not found");
            return;
        }

        // If sender's bank is differ from recipient's bank then we add commission
        if (accountTo.getBankId() != accountFrom.getBankId()) {
            Bank bank;
            try {
                bank = bankRepository.findById(fromBankId);
            } catch (SQLException e) {
                System.out.println("Internal bank error: bank was not found");
                throw new RuntimeException(e);
            }
            amount = amount + bank.getTransferCommission();
        }

        if (accountFrom.getBalance() < amount) {
            System.out.println("Invalid balance");
            return;
        }

        if (accountFrom.getBankId() != fromBankId) {
            System.out.println("Invalid bank ID (sender doesn't have an account in this bank)");
            return;
        }

        if (accountTo.getBankId() != toBankId) {
            System.out.println("Invalid bank ID (recipient doesn't have an account in this bank)");
            return;
        }

        accountRepository.transferMoney(clientFromId, clientToId, fromBankId, toBankId, amount);
        try {
            // hardcoded for now
            String currency = "USD";
            transactionRepository.add(new Transaction(generateRandomAccountNumber(), clientFromId, clientToId, amount, new Date(), currency));
        } catch (SQLException e) {
            System.out.println("Adding transaction was failed");
            throw new RuntimeException(e);
        }

        System.out.println("Transfer was successful");

    }

    // stub
    private int generateRandomAccountNumber() {
        Random random = new Random();
        return random.nextInt(1000000);
    }
}
