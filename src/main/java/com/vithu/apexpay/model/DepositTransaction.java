package com.vithu.apexpay.model;

import java.time.LocalDateTime;

public class DepositTransaction extends Transaction {
    private Account account;

    public DepositTransaction(Account account, String transactionId, double amount) {
        super(transactionId, amount);
        this.account = account;
    }

    @Override
    public void process() {
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        account.deposit(this.getAmount());
        System.out.printf("Deposited %.2f into account %s%n", getAmount(), account.getAccountId());
    }
}
