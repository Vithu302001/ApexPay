package com.vithu.apexpay.service;

import com.vithu.apexpay.exception.AccountNotFoundException;
import com.vithu.apexpay.model.*;
import com.vithu.apexpay.repository.AccountRepository;
import com.vithu.apexpay.repository.TransactionRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class BankService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public BankService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    // --- Account operations ---

    public void createAccount(String accountId, String ownerName) {
        Account account = new Account(accountId, ownerName);
        accountRepository.createAccount(account);
    }

    /** Returns Optional<Account> — caller decides how to handle missing. */
    public Optional<Account> findAccount(String accountId) {
        return accountRepository.findById(accountId);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public boolean accountExists(String accountId) {
        return accountRepository.existsById(accountId);
    }

    public void deleteAccount(String accountId) {
        accountRepository.deleteById(accountId);
    }

    // --- Transaction operations ---

    /**
     * Uses Optional.orElseThrow() to find account or throw.
     */
    public void deposit(String accountId, double amount, String transactionId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
        DepositTransaction depositTransaction = new DepositTransaction(account, transactionId, amount);
        depositTransaction.process();
        transactionRepository.save(depositTransaction);
    }

    public void withdraw(String accountId, double amount, String transactionId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
        WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction(account, transactionId, amount);
        withdrawalTransaction.process();
        transactionRepository.save(withdrawalTransaction);
    }

    public void transfer(String fromAccountId, String toAccountId, double amount, String transactionId) {
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new AccountNotFoundException(fromAccountId));
        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new AccountNotFoundException(toAccountId));

        if (fromAccount.getAccountId().equals(toAccount.getAccountId())) {
            throw new IllegalArgumentException("Cannot transfer to the same account: " + fromAccount.getAccountId());
        }

        TransferTransaction transferTransaction = new TransferTransaction(fromAccount, toAccount, amount, transactionId);
        transferTransaction.process();
        transactionRepository.save(transferTransaction);
    }

    // --- Query operations ---

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getTransactionsByAccount(String accountId) {
        return transactionRepository.findByAccount(accountId);
    }

    public List<Transaction> getTransactionsAbove(double amount) {
        return transactionRepository.findAll().stream()
                .filter(t -> t.getAmount() > amount)
                .toList();
    }

    public long countDeposits() {
        return transactionRepository.findAll().stream()
                .filter(t -> t instanceof DepositTransaction)
                .count();
    }

    public double getTotalDepositedAmount() {
        return transactionRepository.findAll().stream()
                .filter(t -> t instanceof DepositTransaction)
                .mapToDouble(Transaction::getAmount)
                .reduce(0.0, Double::sum);
    }

    public Optional<Transaction> getLargestTransaction() {
        return transactionRepository.findAll().stream()
                .max(Comparator.comparing(Transaction::getAmount));
    }

    // --- Display (console) ---

    public void listAccounts() {
        List<Account> all = accountRepository.findAll();
        if (all.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }
        all.forEach(Account::printAccount);
    }

    /**
     * Demonstrates Optional.ifPresent, isPresent, orElse patterns.
     */
    public void printAccountIfPresent(String accountId) {
        Optional<Account> opt = accountRepository.findById(accountId);
        opt.ifPresent(account ->
                System.out.printf("Found: %s (%s) — balance: %.2f%n",
                        account.getOwnerName(), account.getAccountId(), account.getBalance())
        );
        if (opt.isPresent()) {
            System.out.println("Account exists: " + opt.get().getAccountId());
        }
        String owner = opt.map(Account::getOwnerName).orElse("UNKNOWN");
        System.out.println("Owner (or UNKNOWN): " + owner);
    }
}
