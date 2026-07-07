package com.vithu.apexpay.model;

public class WithdrawalTransaction extends Transaction {

    public WithdrawalTransaction(Account account, String transactionId, double amount) {
        super(transactionId, amount,account);
    }

    @Override
    public void process() {
        if (this.getSrcAccount() == null) {
            System.out.println("Account not found.");
            return;
        }

        this.getSrcAccount().withdraw(this.getAmount());
        System.out.printf("Withdraw %.2f from account %s%n", getAmount(), this.getSrcAccount().getAccountId());
    }

    @Override
    public boolean isAccountInvolved(String accountId) {
        return this.getSrcAccount().getAccountId().equals(accountId);
    }
}
