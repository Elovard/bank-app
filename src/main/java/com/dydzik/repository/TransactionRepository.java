package com.dydzik.repository;

import com.dydzik.entity.Transaction;

import java.sql.SQLException;

public interface TransactionRepository {
    void add(Transaction transaction) throws SQLException;
    Transaction findById(int id) throws SQLException;
}

