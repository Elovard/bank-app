package com.dydzik.repository;

import com.dydzik.entity.Account;
import com.dydzik.entity.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryJDBC implements AccountRepository {
    private final String url = "jdbc:mysql://localhost:3306/bank_app";
    private final String user = "root";
    private final String password = "1122";

    @Override
    public void add(Account account) throws SQLException {
        String SQL = "INSERT INTO accounts(client_id, bank_id, balance, currency) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, account.getClientId());
            pstmt.setInt(2, account.getBankId());
            pstmt.setDouble(3, account.getBalance());
            pstmt.setString(4, account.getCurrency());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        account.setId(rs.getInt(1));
                    }
                }
            }
        }
    }

    @Override
    public Account findById(int id) throws SQLException {
        String SQL = "SELECT * FROM accounts WHERE id = ?";
        Account account = null;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                account = new Account();
                account.setId(rs.getInt("id"));
                account.setClientId(rs.getInt("client_id"));
                account.setBankId(rs.getInt("bank_id"));
                account.setBalance(rs.getDouble("balance"));
                account.setCurrency(rs.getString("currency"));
            }
        }

        return account;
    }

    @Override
    public Account findByClientIdAndBankId(int id, int bankId) {
        String SQL = "SELECT * FROM accounts WHERE client_id = ? AND bank_id = ?";
        Account account = null;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);
            pstmt.setInt(2, bankId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                account = new Account();
                account.setId(rs.getInt("id"));
                account.setClientId(rs.getInt("client_id"));
                account.setBankId(rs.getInt("bank_id"));
                account.setBalance(rs.getDouble("balance"));
                account.setCurrency(rs.getString("currency"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return account;
    }

    @Override
    public List<Account> getClientAccounts(int clientId) {
        String SQL = "SELECT id, client_id, bank_id, balance, currency FROM accounts WHERE client_id = ?";
        List<Account> accounts = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, clientId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                accounts.add(new Account(
                        rs.getInt("id"),
                        rs.getInt("client_id"),
                        rs.getDouble("balance"),
                        rs.getString("currency"),
                        rs.getInt("bank_id"))
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return accounts;
    }

    @Override
    public void transferMoney(int clientFrom, int clientTo, int bankFromId, int bankToId, double amount) {
        String queryToChargeMoney = "UPDATE accounts SET balance = balance - ? WHERE client_id = ? AND bank_id = ?";
        String queryToDepositMoney = "UPDATE accounts SET balance = balance + ? WHERE client_id = ? AND bank_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            conn.setAutoCommit(false);

            try (PreparedStatement chargeStmt = conn.prepareStatement(queryToChargeMoney);
                 PreparedStatement depositStmt = conn.prepareStatement(queryToDepositMoney)) {

                // Charge money
                chargeStmt.setDouble(1, amount);
                chargeStmt.setInt(2, clientFrom);
                chargeStmt.setInt(3, bankFromId);
                int chargeRowsAffected = chargeStmt.executeUpdate();

                // Deposit money
                depositStmt.setDouble(1, amount);
                depositStmt.setInt(2, clientTo);
                depositStmt.setInt(3, bankToId);
                int depositRowsAffected = depositStmt.executeUpdate();

                if (chargeRowsAffected == 1 && depositRowsAffected == 1) {
                    conn.commit();
                } else {
                    conn.rollback();
                    throw new RuntimeException("Transaction failed. Rollback executed.");
                }
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException("Transaction failed. Rollback executed.", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
