package com.vithu.apexpay.model;

import java.time.LocalDateTime;

public abstract class Transaction {
    private String transactionId;
    private double amount;
    private LocalDateTime timestamp;

    public Transaction(String transactionId, double amount) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    public abstract void process();
    public String getTransactionId() {
        return transactionId;
    }
    public double getAmount() {
        return amount;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
