package com.dydzik.entity;

public class Account {
    private int id;
    private int clientId;
    private double balance;
    private String currency;
    private int bankId;

    public Account() {

    }

    public Account(int id, int clientId, double balance, String currency, int bankId) {
        this.id = id;
        this.clientId = clientId;
        this.balance = balance;
        this.currency = currency;
        this.bankId = bankId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Account ID " + id + " | User ID " + clientId + " | Bank ID " + bankId + " | Total Balance: " + balance;
    }
}
