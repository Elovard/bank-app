package com.dydzik.repository;

import com.dydzik.entity.BusinessClient;
import com.dydzik.entity.Client;
import com.dydzik.entity.IndividualClient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepositoryJDBC implements ClientRepository {
    private final String url = "jdbc:mysql://localhost:3306/bank_app";
    private final String user = "root";
    private final String password = "1122";

    @Override
    public void add(Client client) throws SQLException {
        String SQL = "INSERT INTO clients(type, name) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, client.getType());
            pstmt.setString(2, client.getName());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        client.setId(rs.getInt(1));
                    }
                }
            }
        }
    }

    @Override
    public Client findById(int id) {
        String SQL = "SELECT * FROM clients WHERE id = ?";
        Client client = null;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String type = rs.getString("type");
                // todo add builder
                if (type.equalsIgnoreCase("individual")) {
                    client = new IndividualClient(rs.getString("type"), rs.getInt("id"), rs.getString("name"));
                } else {
                    client = new BusinessClient(rs.getString("type"), rs.getInt("id"), rs.getString("name"));
                }
            } else {
                System.out.println("Client not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return client;
    }

    @Override
    public List<Client> findAllClients() {
        String SQL = "SELECT * FROM clients";
        List<Client> clients = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String type = rs.getString("type");
                // todo add builder
                if (type.equalsIgnoreCase("individual")) {
                    clients.add(new IndividualClient(rs.getString("type"), rs.getInt("id"), rs.getString("name")));
                } else {
                    clients.add(new BusinessClient(rs.getString("type"), rs.getInt("id"), rs.getString("name")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return clients;
    }

}
