package com.vithu.apexpay.repository;

import com.vithu.apexpay.model.Account;

import java.util.*;

public class AccountRepository implements BaseRepository<Account, String> {

    private final Map<String, Account> accounts = new HashMap<>();

    @Override
    public Account save(Account account) {
        accounts.put(account.getAccountId(), account);
        return account;
    }

    @Override
    public Optional<Account> findById(String accountId) {
        return Optional.ofNullable(accounts.get(accountId));
    }

    @Override
    public List<Account> findAll() {
        return List.copyOf(accounts.values());
    }

    @Override
    public boolean existsById(String accountId) {
        return accounts.containsKey(accountId);
    }

    @Override
    public void deleteById(String accountId) {
        accounts.remove(accountId);
    }

    /** Creates a new account only if one doesn't already exist. */
    public Account createAccount(Account account) {
        if (accounts.containsKey(account.getAccountId())) {
            throw new IllegalArgumentException("Account already exists: " + account.getAccountId());
        }
        return save(account);
    }
}
