package com.vithu.apexpay.repository;

import com.vithu.apexpay.model.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionRepository implements BaseRepository<Transaction, String> {

    private final List<Transaction> transactions = new ArrayList<>();

    @Override
    public Transaction save(Transaction transaction) {
        transactions.add(transaction);
        return transaction;
    }

    @Override
    public Optional<Transaction> findById(String id) {
        return transactions.stream()
                .filter(t -> t.getTransactionId().equals(id))
                .findFirst();
    }

    @Override
    public List<Transaction> findAll() {
        return List.copyOf(transactions);
    }

    @Override
    public boolean existsById(String id) {
        return transactions.stream().anyMatch(t -> t.getTransactionId().equals(id));
    }

    @Override
    public void deleteById(String id) {
        transactions.removeIf(t -> t.getTransactionId().equals(id));
    }

    public List<Transaction> findByAccount(String accountId) {
        return transactions.stream()
                .filter(t -> t.isAccountInvolved(accountId))
                .toList();
    }
}
