package com.dydzik.command;

import com.dydzik.entity.Account;
import com.dydzik.entity.Bank;
import com.dydzik.entity.Client;
import com.dydzik.factory.ClientFactory;
import com.dydzik.repository.AccountRepository;
import com.dydzik.repository.BankRepository;
import com.dydzik.repository.ClientRepository;

import java.sql.SQLException;
import java.util.Random;

public class CreateClientCommand implements Command {
    private ClientRepository clientRepository;
    private AccountRepository accountRepository;
    private BankRepository bankRepository;

    public CreateClientCommand(ClientRepository clientRepository, AccountRepository accountRepository, BankRepository bankRepository) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
        this.bankRepository = bankRepository;
    }

    @Override
    public void execute(String[] args) {

        if (args.length < 3) {
            System.out.println("Insufficient arguments. Usage: client create <individual/business> <name> <bankId>");
            return;
        }
        String type = args[0];
        String name = args[1];
        int bankId = Integer.parseInt(args[2]);

        if (!type.equalsIgnoreCase("individual") && !type.equalsIgnoreCase("business")) {
            System.out.println("Allowed client types: individual / business");
            return;
        }

        //todo handle exception
        Bank bank = null;
        try {
            bank = bankRepository.findById(bankId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        if (bank == null) {
            System.out.println("Bank with ID " + bankId + " not found.");
            return;
        }

        //todo handle exception
        Client client = ClientFactory.getClient(type, generateRandomAccountNumber(), name);
        try {
            clientRepository.add(client);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        //todo handle exception
        int accountNumber = generateRandomAccountNumber();
        Account account = new Account(accountNumber, client.getId(), 0.0, "USD", bankId);
        try {
            accountRepository.add(account);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        System.out.println("Client added successfully with ID: " + client.getId());
    }

    // stub
    private int generateRandomAccountNumber() {
        Random random = new Random();
        return random.nextInt(1000000);
    }
}
