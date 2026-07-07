package com.vithu.apexpay.repository;

import com.vithu.apexpay.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {

    private final List<Transaction> transactions;

    public TransactionRepository() {
        transactions = new ArrayList<>();
    }

    public void save(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> findAll(){
        return transactions;
    }

    public List<Transaction> findByAccount(String accountId){
        List<Transaction> result = new ArrayList<>();
        for(Transaction transaction : transactions){
            if(transaction.isAccountInvolved(accountId)){
                result.add(transaction);
            }
        }
        return result;
    }

}