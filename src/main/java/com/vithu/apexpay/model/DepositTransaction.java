package com.vithu.apexpay.model;

import java.time.LocalDateTime;

public class DepositTransaction extends Transaction {

    public DepositTransaction(Account account, String transactionId, double amount) {
        super(transactionId, amount ,account);
    }

    @Override
    public void process() {
        if (this.getSrcAccount() == null) {
            System.out.println("Account not found.");
            return;
        }

        this.getSrcAccount().deposit(this.getAmount());
        System.out.printf("Deposited %.2f into account %s%n", getAmount(), this.getSrcAccount().getAccountId());
    }

    @Override
    public boolean isAccountInvolved(String accountId) {
        return this.getSrcAccount().getAccountId().equals(accountId);
    }
}
