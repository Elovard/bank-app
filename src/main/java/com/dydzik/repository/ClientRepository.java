package com.dydzik.repository;

import com.dydzik.entity.Client;

import java.sql.SQLException;
import java.util.List;

public interface ClientRepository {
    void add(Client client) throws SQLException;
    Client findById(int id);
    List<Client> findAllClients();
}
