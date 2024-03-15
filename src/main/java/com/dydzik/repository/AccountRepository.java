package com.dydzik.repository;

import com.dydzik.entity.Account;

import java.sql.SQLException;
import java.util.List;

public interface AccountRepository {
    void add(Account account) throws SQLException;
    Account findById(int id) throws SQLException;
    Account findByClientIdAndBankId(int id, int bankId) throws SQLException;
    List<Account> getClientAccounts(int clientId);
    void transferMoney(int clientFrom, int clientTo, int bankFromId, int bankToId, double amount);
}
