package com.vithu.apexpay;

import com.vithu.apexpay.model.Account;
import com.vithu.apexpay.model.DepositTransaction;
import com.vithu.apexpay.model.TransferTransaction;
import com.vithu.apexpay.model.WithdrawalTransaction;
import com.vithu.apexpay.repository.AccountRepository;

public class Main {
    public static void main(String[] args) {
        AccountRepository accountRepository = new AccountRepository();
        Account a1 = new Account("ACC001","Vithu");
        Account a2 = new Account("ACC002","Alice");

        accountRepository.createAccount(a1);
        accountRepository.createAccount(a2);

        DepositTransaction d1 = new DepositTransaction(a1,"T001",5000);
        WithdrawalTransaction w1 = new WithdrawalTransaction(a1,"T002",1000);
        TransferTransaction t1 = new TransferTransaction(a1,a2,2000,"T003");

        d1.process();
        w1.process();
        t1.process();

        accountRepository.listAccounts();
    }
}
