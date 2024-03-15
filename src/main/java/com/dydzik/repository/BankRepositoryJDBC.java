package com.dydzik.repository;

import com.dydzik.entity.Bank;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BankRepositoryJDBC implements BankRepository {
    private final String url = "jdbc:mysql://localhost:3306/bank_app";
    private final String user = "root";
    private final String password = "1122";

    @Override
    public void add(Bank bank) throws SQLException {
        String SQL = "INSERT INTO banks(name, commission_rate) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, bank.getName());
            pstmt.setDouble(2, bank.getTransferCommission());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        bank.setId(rs.getInt(1));
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Bank findById(int id) {
        String SQL = "SELECT * FROM banks WHERE id = ?";
        Bank bank = null;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                bank = new Bank();
                bank.setId(rs.getInt("id"));
                bank.setName(rs.getString("name"));
                bank.setTransferCommission(rs.getDouble("commission_rate"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return bank;
    }

    @Override
    public void delete(int id) throws SQLException {
        String SQL = "DELETE FROM banks WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            System.out.println("Bank with ID " + id + " was deleted");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void updateCommission(int id, double commission) throws SQLException {
        String SQL = "UPDATE banks SET commission_rate = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setDouble(1, commission);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();

            System.out.println("Bank with ID " + id + " was updated");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void updateName(int id, String name) throws SQLException {
        String SQL = "UPDATE banks SET name = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();

            System.out.println("Bank with ID " + id + " was updated");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Bank> getAllBanks() {
        String SQL = "SELECT id, name, commission_rate FROM banks";
        List<Bank> banks = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                banks.add(new Bank(rs.getInt("id"), rs.getString("name"), rs.getDouble("commission_rate")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return banks;
    }

}
