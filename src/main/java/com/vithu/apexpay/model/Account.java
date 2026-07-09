package com.vithu.apexpay.model;

import com.vithu.apexpay.exception.InsufficientFundsException;

public class Account {
    private String accountId;
    private String ownerName;
    private double balance;

    public Account(String accountId, String ownerName) {
        this.accountId = accountId;
        this.ownerName = ownerName;
        this.balance = 0.0;
    }

    public void deposit(double amount){
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero.");
        }

        this.balance += amount;

    }

    public void withdraw(double amount){
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
        }

        if (this.balance < amount) {
            throw new InsufficientFundsException(this.accountId, this.balance, amount);
        }

        this.balance -= amount;
    }

    public void printAccount(){
        System.out.printf("Account ID : %s%n", accountId);
        System.out.printf("Owner      : %s%n", ownerName);
        System.out.printf("Balance    : %.2f%n", balance);
    }

    public String getAccountId() {
        return accountId;
    }
    public String getOwnerName() {
        return ownerName;
    }
    public double getBalance() {
        return balance;
    }
}
