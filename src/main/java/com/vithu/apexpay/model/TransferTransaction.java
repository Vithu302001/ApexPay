package com.vithu.apexpay.model;

public class TransferTransaction extends Transaction {
    private Account desAccount;

    public TransferTransaction(Account fromAccount, Account desAccount,double amount,String transactionId) {
        super(transactionId,amount,fromAccount);
        this.desAccount = desAccount;
    }

    @Override
    public void process() {
        if(this.getSrcAccount() == null) {
            System.out.println("fromAccount not found");
            return;
        }
        if(desAccount == null) {
            System.out.println("desAccount not found");
            return;
        }


        this.getSrcAccount().withdraw(this.getAmount());
        desAccount.deposit(this.getAmount());
        System.out.printf("Transferred %.2f from account %s%n to %s%n", getAmount(), this.getSrcAccount().getAccountId(),desAccount.getAccountId());

    }

    @Override
    public boolean isAccountInvolved(String accountId) {
        return this.getSrcAccount().getAccountId().equals(accountId) || desAccount.getAccountId().equals(accountId);
    }


}
