package com.vithu.apexpay.dto;

import jakarta.validation.constraints.Positive;

public class WithdrawRequest {

    @Positive(message = "Withdrawal amount must be positive")
    private double amount;

    public WithdrawRequest() {}

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}
