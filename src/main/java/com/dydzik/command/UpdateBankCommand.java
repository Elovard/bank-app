package com.dydzik.command;

import com.dydzik.entity.Bank;
import com.dydzik.repository.BankRepository;

import java.sql.SQLException;

public class UpdateBankCommand implements Command {
    private BankRepository bankRepository;

    public UpdateBankCommand(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 3) {
            System.out.println("Insufficient arguments. Usage: bank update <id> <commission/name> <value>");
            return;
        }

        String operationType = args[1];
        if (!operationType.equalsIgnoreCase("commission") && !operationType.equalsIgnoreCase("name")) {
            System.out.println("Invalid update argument (commission / name are available)");
            return;
        }

        int bankId = Integer.parseInt(args[0]);
        if (operationType.equalsIgnoreCase("commission")) {

            try {
                Bank bank = bankRepository.findById(bankId);
                if (bank == null) {
                    System.out.println("Bank with id " + bankId + " is not found");
                    return;
                }
                bankRepository.updateCommission(bankId, Integer.parseInt(args[2]));
            } catch (SQLException e) {
                // todo handle
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        } else {
            try {
                Bank bank = bankRepository.findById(bankId);
                if (bank == null) {
                    System.out.println("Bank with id " + bankId + " is not found");
                    return;
                }
                bankRepository.updateName(bankId, args[2]);
            } catch (SQLException e) {
                // todo handle
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
