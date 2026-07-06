package com.vithu.apexpay.model;

public class TransferTransaction extends Transaction {
    private Account fromAccount;
    private Account toAccount;

    public TransferTransaction(Account fromAccount, Account toAccount,double amount,String transactionId) {
        super(transactionId,amount);
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }

    @Override
    public void process() {
        if(fromAccount == null) {
            System.out.println("fromAccount not found");
            return;
        }
        if(toAccount == null) {
            System.out.println("toAccount not found");
            return;
        }


        fromAccount.withdraw(this.getAmount());
        toAccount.deposit(this.getAmount());
        System.out.printf("Transferred %.2f from account %s%n to %s%n", getAmount(), fromAccount.getAccountId(),toAccount.getAccountId());

    }
}
