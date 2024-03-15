package com.dydzik.repository;

import com.dydzik.entity.Bank;

import java.sql.SQLException;
import java.util.List;

public interface BankRepository {
    void add(Bank bank) throws SQLException;
    Bank findById(int id) throws SQLException;
    void delete(int id) throws SQLException;
    void updateCommission(int id, double commission) throws SQLException;
    void updateName(int id, String name) throws SQLException;
    List<Bank> getAllBanks();
}
