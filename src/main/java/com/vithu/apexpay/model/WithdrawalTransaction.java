package com.vithu.apexpay.model;

public class WithdrawalTransaction extends Transaction {
    private Account account;

    public WithdrawalTransaction(Account account, String transactionId, double amount) {
        super(transactionId, amount);
        this.account = account;
    }

    @Override
    public void process() {
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        account.withdraw(this.getAmount());
        System.out.printf("Withdraw %.2f from account %s%n", getAmount(), account.getAccountId());
    }
}
