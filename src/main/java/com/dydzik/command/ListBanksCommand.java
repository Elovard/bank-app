package com.dydzik.command;

import com.dydzik.entity.Bank;
import com.dydzik.repository.BankRepository;

import java.util.List;

public class ListBanksCommand implements Command {
    private BankRepository bankRepository;

    public ListBanksCommand(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            System.out.println("Insufficient arguments. Usage: bank list");
            return;
        }

        List<Bank> banks = bankRepository.getAllBanks();
        if (banks.isEmpty()) {
            System.out.println("No banks found");
        } else {
            System.out.println("The list of banks: ");
            banks.forEach(System.out::println);
        }
    }
}
