package com.vithu.apexpay.dto;

import com.vithu.apexpay.model.Account;

public class AccountResponse {
    private String accountId;
    private String ownerName;
    private double balance;

    public AccountResponse() {}

    public static AccountResponse from(Account account) {
        AccountResponse dto = new AccountResponse();
        dto.accountId = account.getAccountId();
        dto.ownerName = account.getOwnerName();
        dto.balance = account.getBalance();
        return dto;
    }

    public String getAccountId() { return accountId; }
    public String getOwnerName() { return ownerName; }
    public double getBalance() { return balance; }
}
