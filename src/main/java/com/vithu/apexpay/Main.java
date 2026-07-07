package com.vithu.apexpay;

import com.vithu.apexpay.model.Account;
import com.vithu.apexpay.model.Transaction;
import com.vithu.apexpay.repository.AccountRepository;
import com.vithu.apexpay.repository.TransactionRepository;
import com.vithu.apexpay.service.BankService;

public class Main {

    public static void main(String[] args) {

        // Repositories
        AccountRepository accountRepository = new AccountRepository();
        TransactionRepository transactionRepository = new TransactionRepository();

        // Service
        BankService bankService = new BankService(accountRepository, transactionRepository);

        // Create accounts
        bankService.createAccount("ACC001", "Alice");
        bankService.createAccount("ACC002", "Bob");
        bankService.createAccount("ACC003", "Charlie");

        // Deposit to their accounts
        bankService.deposit("ACC001", 1000, "TXN001");
        bankService.deposit("ACC002", 500, "TXN002");
        bankService.deposit("ACC003", 2000, "TXN003");

        bankService.listAccounts();

        // Deposit
        bankService.deposit("ACC001", 500, "TXN004");

        // Withdraw
        bankService.withdraw("ACC002", 100, "TXN005");

        // Transfer
        bankService.transfer("ACC001", "ACC003", 300, "TXN006");

        // Invalid Account
        bankService.deposit("ACC999", 1000, "TXN007");

        // Same Account Transfer
        bankService.transfer("ACC001", "ACC001", 100, "TXN008");

        // Print Accounts
        bankService.listAccounts();

        // Print All Transactions
        for (Transaction transaction : bankService.getAllTransactions()) {
            System.out.println(transaction);
        }

        // Transactions of ACC001
        for (Transaction transaction : bankService.getTransactionsByAccount("ACC001")) {
            System.out.println(transaction);
        }

        // Transactions of ACC003
        for (Transaction transaction : bankService.getTransactionsByAccount("ACC003")) {
            System.out.println(transaction);
        }
    }
}