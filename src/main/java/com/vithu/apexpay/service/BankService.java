package com.vithu.apexpay.service;

import com.vithu.apexpay.model.*;
import com.vithu.apexpay.repository.AccountRepository;
import com.vithu.apexpay.repository.TransactionRepository;

import java.util.List;

public class BankService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public BankService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public void createAccount(String accountId, String ownerName){
        Account account = new Account(accountId, ownerName);
        accountRepository.createAccount(account);
    }

    public void deposit(String accountId, double amount, String transactionId){
        Account account = accountRepository.findAccount(accountId);

        if(account == null){
            System.out.printf("No such account %s check again",accountId);
            return;
        }

        DepositTransaction depositTransaction = new DepositTransaction(account,transactionId,amount);
        depositTransaction.process();
        transactionRepository.save(depositTransaction);
    }

    public void withdraw(String accountId, double amount, String transactionId){
        Account account = accountRepository.findAccount(accountId);

        if(account == null){
            System.out.printf("No such account %s check again",accountId);
            return;
        }

        WithdrawalTransaction  withdrawalTransaction = new WithdrawalTransaction(account,transactionId,amount);
        withdrawalTransaction.process();
        transactionRepository.save(withdrawalTransaction);
    }

    public void transfer(String fromAccountId, String toAccountId, double amount, String transactionId){
        Account fromAccount = accountRepository.findAccount(fromAccountId);
        Account toAccount = accountRepository.findAccount(toAccountId);

        if(fromAccount == null){
            System.out.printf("No such account %s check again",fromAccountId);
            return;
        }

        if(toAccount == null){
            System.out.printf("No such account %s check again",toAccountId);
            return;
        }

        //we can allow a user to transfer from his one account to another account he owns. but not to the exact same account.
        if (fromAccount.getAccountId().equals(toAccount.getAccountId())) {
            System.out.println("You can't transfer to the same account");
            return;
        }

        TransferTransaction transferTransaction = new TransferTransaction(fromAccount,toAccount,amount,transactionId);
        transferTransaction.process();
        transactionRepository.save(transferTransaction);
    }

    public void listAccounts() {
        accountRepository.listAccounts();
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getTransactionsByAccount(String accountId) {
        return transactionRepository.findByAccount(accountId);
    }
}
