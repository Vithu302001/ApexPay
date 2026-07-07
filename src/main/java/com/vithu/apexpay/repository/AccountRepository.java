package com.vithu.apexpay.repository;

import com.vithu.apexpay.model.Account;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AccountRepository {
    private final Map<String, Account> accounts;

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

    public void deleteAccount(String accountId){
        if (accounts.remove(accountId) == null) {
            System.out.printf("Account %s does not exist%n", accountId);
        }
    }

    public boolean exists(String accountId){
        return accounts.containsKey(accountId);
    }

    public Collection<Account> getAllAccounts(){
        return accounts.values();
    }

}
