package com.vithu.apexpay.model;

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
            System.out.println("Deposit amount must be greater than zero.");
            return;
        }

        this.balance += amount;

    }

    public void withdraw(double amount){
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be greater than zero.");
            return;
        }

        if (this.balance<amount) {
            System.out.println("Insufficient balance");
            return;
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
