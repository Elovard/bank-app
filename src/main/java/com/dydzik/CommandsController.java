package com.dydzik;

import com.dydzik.command.*;
import com.dydzik.repository.*;

public class CommandsController {
    private static final BankRepository bankRepository = new BankRepositoryJDBC();
    private static final ClientRepository clientRepository = new ClientRepositoryJDBC();
    private static final AccountRepository accountRepository = new AccountRepositoryJDBC();
    private static final TransactionRepository transactionRepository = new TransactionRepositoryJDBC();

    public void processCommand(String input) {
        String[] tokens = input.split(" ");
        if (tokens.length < 1) {
            System.out.println("Invalid command. Usage: <entity> <command> or type help");
            return;
        }

        String command = tokens[0];

        if (!command.equalsIgnoreCase("help") && tokens.length == 1) {
            System.out.println("Invalid command. Usage: <entity> <command> or type help");
            return;
        } else if (command.equalsIgnoreCase("help")) {
            System.out.println("-----------------------------------------------------------------");
            System.out.println("Use entity name followed by the operation you want to execute;");
            System.out.println("The command shall have the following format <entity> <command>;");
            System.out.println("Available commands: create / update / find / delete / list / transfer (client only)");
            System.out.println("Use <entity> <command> to see available params for each operation");
            System.out.println("Example: bank create");
            System.out.println("-----------------------------------------------------------------");
            return;
        }

        String subCommand = tokens[1];
        String[] args = new String[tokens.length - 2];
        System.arraycopy(tokens, 2, args, 0, args.length);

        if (command.equalsIgnoreCase("bank")) {
            switch (subCommand.toLowerCase()) {
                case "create":
                    Command createBankCommand = new CreateBankCommand(bankRepository);
                    createBankCommand.execute(args);
                    break;

                case "delete":
                    Command deleteBankCommand = new DeleteBankCommand(bankRepository);
                    deleteBankCommand.execute(args);
                    break;

                case "update":
                    Command updateBankCommand = new UpdateBankCommand(bankRepository);
                    updateBankCommand.execute(args);
                    break;

                case "list":
                    Command listAllBanksCommand = new ListBanksCommand(bankRepository);
                    listAllBanksCommand.execute(args);
                    break;

                default:
                    System.out.println("Unknown command: " + command);
                    break;
            }
        } else if (command.equalsIgnoreCase("client")) {
            switch (subCommand.toLowerCase()) {
                case "create":
                    Command addClientCommand = new CreateClientCommand(clientRepository, accountRepository, bankRepository);
                    addClientCommand.execute(args);
                    break;

                case "find":
                    Command getClientByIdCommand = new GetClientByIdCommand(clientRepository);
                    getClientByIdCommand.execute(args);
                    break;

                case "list":
                    Command getAllClientsCommand = new ListClientsCommand(clientRepository);
                    getAllClientsCommand.execute(args);
                    break;

                case "accounts":
                    Command listClientAccountsCommand = new ListClientAccountsCommand(clientRepository, accountRepository);
                    listClientAccountsCommand.execute(args);
                    break;

                case "transfer":
                    Command transferMoneyCommand = new TransferFromClientToClientCommand(clientRepository, transactionRepository, accountRepository, bankRepository);
                    transferMoneyCommand.execute(args);
                    break;

                default:
                    System.out.println("Unknown command: " + command);
                    break;
            }
        } else if (command.equalsIgnoreCase("account")) {

            // account actions

        } else {
            System.out.println("Unknown command. Use help");
        }
    }
}
