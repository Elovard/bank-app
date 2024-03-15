package com.dydzik.command;

import com.dydzik.entity.Client;
import com.dydzik.repository.ClientRepository;

import java.util.List;

public class ListClientsCommand implements Command {
    private ClientRepository clientRepository;

    public ListClientsCommand(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void execute(String[] args) {
        List<Client> clients = clientRepository.findAllClients();
        if (clients.isEmpty()) {
            System.out.println("No clients found");
        } else {
            clients.forEach(System.out::println);
        }
    }
}
