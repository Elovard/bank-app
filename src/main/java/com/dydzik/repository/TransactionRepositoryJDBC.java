package com.dydzik.repository;

import com.dydzik.entity.Transaction;

import java.sql.*;

public class TransactionRepositoryJDBC implements TransactionRepository {
    private final String url = "jdbc:mysql://localhost:3306/bank_app";
    private final String user = "root";
    private final String password = "1122";

    @Override
    public void add(Transaction transaction) throws SQLException {
        String SQL = "INSERT INTO transactions(from_account_id, to_account_id, amount, currency) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, transaction.getFromAccountId());
            pstmt.setInt(2, transaction.getToAccountId());
            pstmt.setDouble(3, transaction.getAmount());
            pstmt.setString(4, transaction.getCurrency());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        transaction.setId(rs.getInt(1));
                    }
                }
            }
        }
    }

    @Override
    public Transaction findById(int id) throws SQLException {
        String SQL = "SELECT * FROM transactions WHERE id = ?";
        Transaction transaction = null;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                transaction = new Transaction();
                transaction.setId(rs.getInt("id"));
                transaction.setFromAccountId(rs.getInt("from_account_id"));
                transaction.setToAccountId(rs.getInt("to_account_id"));
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setCurrency(rs.getString("currency"));
                transaction.setTransactionDate(rs.getTimestamp("transaction_date"));
            }
        }

        return transaction;
    }

}
