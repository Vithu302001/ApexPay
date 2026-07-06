package com.vithu.apexpay.repository;

import com.vithu.apexpay.model.Account;

import java.util.HashMap;
import java.util.Map;

public class AccountRepository {
    private Map<String, Account> accounts;

    public AccountRepository() {
        accounts = new HashMap<>();
    }

    public void createAccount(Account account){
        if (accounts.containsKey(account.getAccountId())) {
            System.out.printf("Account %s already exists%n", account.getAccountId());
            return;
        }
        accounts.put(account.getAccountId(), account);
    }
    public Account findAccount(String accountId){
        return accounts.get(accountId);
    }
    public void listAccounts(){
        for(Account account:accounts.values()){
            account.printAccount();
            System.out.println();
        }
    }
}
