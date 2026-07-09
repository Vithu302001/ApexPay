package com.vithu.apexpay.exception;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String accountId, double balance, double attempted) {
        super(String.format("Insufficient funds in account %s: balance=%.2f, attempted=%.2f",
                accountId, balance, attempted));
    }
}
