package com.vithu.apexpay.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class TransferRequest {

    @NotBlank(message = "Destination account ID is required")
    private String toAccountId;

    @Positive(message = "Transfer amount must be positive")
    private double amount;

    public TransferRequest() {}

    public String getToAccountId() { return toAccountId; }
    public void setToAccountId(String toAccountId) { this.toAccountId = toAccountId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}
