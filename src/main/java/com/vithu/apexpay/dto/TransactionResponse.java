package com.vithu.apexpay.dto;

import com.vithu.apexpay.model.Transaction;

import java.time.LocalDateTime;

public class TransactionResponse {
    private String transactionId;
    private String type;
    private double amount;
    private LocalDateTime timestamp;
    private String fromAccountId;
    private String toAccountId;

    public TransactionResponse() {}

    public static TransactionResponse from(Transaction txn) {
        TransactionResponse dto = new TransactionResponse();
        dto.transactionId = txn.getTransactionId();
        dto.type = txn.getClass().getSimpleName();
        dto.amount = txn.getAmount();
        dto.timestamp = txn.getTimestamp();
        dto.fromAccountId = txn.getSrcAccount() != null
                ? txn.getSrcAccount().getAccountId() : null;
        return dto;
    }

    public String getTransactionId() { return transactionId; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getFromAccountId() { return fromAccountId; }
    public String getToAccountId() { return toAccountId; }
    public void setToAccountId(String toAccountId) { this.toAccountId = toAccountId; }
}
