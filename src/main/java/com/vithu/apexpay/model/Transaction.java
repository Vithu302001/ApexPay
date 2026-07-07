package com.vithu.apexpay.model;

import java.time.LocalDateTime;

public abstract class Transaction {
    private String transactionId;
    private double amount;
    private LocalDateTime timestamp;
    private Account srcAccount;

    public Transaction(String transactionId, double amount , Account srcAccount) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.srcAccount =  srcAccount;
    }

    public abstract void process();
    public abstract boolean isAccountInvolved(String accountId);
    public String getTransactionId() {
        return transactionId;
    }
    public double getAmount() {
        return amount;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public Account getSrcAccount() {
        return srcAccount;
    }

    @Override
    public String toString() {
        return String.format(
                "%s | %s | %.2f | %s",
                transactionId,
                getClass().getSimpleName(),
                amount,
                timestamp
        );
    }

}
