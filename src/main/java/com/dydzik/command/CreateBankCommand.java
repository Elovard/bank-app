package com.dydzik.command;

import com.dydzik.entity.Bank;
import com.dydzik.repository.BankRepository;

import java.sql.SQLException;
import java.util.Random;

public class CreateBankCommand implements Command {
    private BankRepository bankRepository;

    public CreateBankCommand(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public void execute(String[] args) {

        if (args.length < 2) {
            System.out.println("Insufficient arguments. Usage: bank create <name> <commission>");
            return;
        }
        String name = args[0];
        double commission = Double.parseDouble(args[1]);
        int bankNo = generateRandomBankNumber();
        Bank bank = new Bank(bankNo, name, commission);

        // todo handle exception
        try {
            bankRepository.add(bank);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        System.out.println("Bank created successfully with ID: " + bank.getId());
    }

    private int generateRandomBankNumber() {
        Random random = new Random();
        return random.nextInt(1000000);
    }
}
