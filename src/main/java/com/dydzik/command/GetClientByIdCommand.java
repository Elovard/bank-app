package com.dydzik.command;

import com.dydzik.entity.Client;
import com.dydzik.repository.ClientRepository;

public class GetClientByIdCommand implements Command {
    private ClientRepository clientRepository;

    public GetClientByIdCommand(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Insufficient arguments. Usage: client find <id>");
            return;
        }

        int clientId = Integer.parseInt(args[0]);
        Client client = clientRepository.findById(clientId);
        if (client == null) {
            System.out.println("Client not found");
            return;
        }
        System.out.println("Client id " + client.getId() + " with name " + client.getName() + " | Account Type: "  + client.getType());
    }
}
