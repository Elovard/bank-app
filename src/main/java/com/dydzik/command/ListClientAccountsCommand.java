package com.dydzik.command;

import com.dydzik.entity.Client;
import com.dydzik.repository.AccountRepository;
import com.dydzik.repository.ClientRepository;

public class ListClientAccountsCommand implements Command {
    private ClientRepository clientRepository;
    private AccountRepository accountRepository;

    public ListClientAccountsCommand(ClientRepository clientRepository, AccountRepository accountRepository) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Insufficient arguments. Usage: client accounts <id>");
            return;
        }

        int clientId = Integer.parseInt(args[0]);

        Client client = clientRepository.findById(clientId);
        if (client == null) {
            System.out.println("Client with id " + clientId + " is not found");
            return;
        }

        System.out.println("User accounts: ");
        accountRepository.getClientAccounts(clientId).forEach(System.out::println);
    }
}
