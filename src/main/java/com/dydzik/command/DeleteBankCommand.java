package com.dydzik.command;

import com.dydzik.entity.Bank;
import com.dydzik.repository.BankRepository;

import java.sql.SQLException;

public class DeleteBankCommand implements Command {
    private BankRepository bankRepository;

    public DeleteBankCommand(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Insufficient arguments. Usage: bank delete <id>");
            return;
        }

        int bankId = Integer.parseInt(args[0]);

        try {
            Bank bank = bankRepository.findById(bankId);
            if (bank == null) {
                System.out.println("Bank with id " + bankId + " is not found");
                return;
            }
            bankRepository.delete(bankId);
        } catch (SQLException e) {
            // todo handle
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
