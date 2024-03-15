package com.dydzik.entity;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private int id;
    private String name;
    private double transferCommission;
    private List<Account> accounts;

    public Bank() {

    }

    public Bank(int id, String name, double transferCommission) {
        this.id = id;
        this.name = name;
        this.transferCommission = transferCommission;
        this.accounts = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTransferCommission() {
        return transferCommission;
    }

    public void setTransferCommission(double transferCommission) {
        this.transferCommission = transferCommission;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    @Override
    public String toString() {
        return "Bank " + name + " with ID " + id + " and commission rate " + transferCommission;
    }
}
