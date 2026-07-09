package com.vithu.apexpay.service;

import com.vithu.apexpay.model.Account;
import com.vithu.apexpay.model.Transaction;
import com.vithu.apexpay.repository.AccountRepository;
import com.vithu.apexpay.repository.TransactionRepository;

import java.io.*;
import java.nio.file.*;
import java.util.List;

/**
 * Handles File I/O — exporting accounts/transactions to CSV/text
 * and importing accounts from CSV.
 * Demonstrates BufferedReader, BufferedWriter, and try-with-resources.
 */
public class FilePersistenceService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public FilePersistenceService(AccountRepository accountRepository,
                                  TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    /**
     * Exports all accounts to a CSV file.
     * Format: accountId,ownerName,balance
     */
    public void exportAccountsToCsv(Path filePath) throws IOException {
        List<Account> accounts = accountRepository.findAll();

        // try-with-resources ensures BufferedWriter is closed automatically
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            // Header
            writer.write("accountId,ownerName,balance");
            writer.newLine();

            for (Account acc : accounts) {
                writer.write(String.format("%s,%s,%.2f",
                        acc.getAccountId(), acc.getOwnerName(), acc.getBalance()));
                writer.newLine();
            }
        }

        System.out.printf("Exported %d accounts to %s%n", accounts.size(), filePath);
    }

    /**
     * Imports accounts from a CSV file.
     * Skips header row. Skips malformed lines.
     */
    public void importAccountsFromCsv(Path filePath) throws IOException {
        if (!Files.exists(filePath)) {
            System.out.println("File not found: " + filePath);
            return;
        }

        int imported = 0;
        int skipped = 0;

        // try-with-resources with BufferedReader
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {
                // Skip header
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                line = line.trim();
                if (line.isEmpty()) {
                    skipped++;
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length < 3) {
                    System.out.println("Skipping malformed line: " + line);
                    skipped++;
                    continue;
                }

                try {
                    String accountId = parts[0].trim();
                    String ownerName = parts[1].trim();
                    double balance = Double.parseDouble(parts[2].trim());

                    Account account = new Account(accountId, ownerName);
                    // Deposit the initial balance
                    if (balance > 0) {
                        account.deposit(balance);
                    }
                    accountRepository.createAccount(account);
                    imported++;
                } catch (IllegalArgumentException e) {
                    System.out.println("Skipping duplicate/invalid account: " + e.getMessage());
                    skipped++;
                }
            }
        }

        System.out.printf("Imported %d accounts (skipped %d) from %s%n",
                imported, skipped, filePath);
    }

    /**
     * Exports all transactions to a text file.
     */
    public void exportTransactionsToText(Path filePath) throws IOException {
        List<Transaction> transactions = transactionRepository.findAll();

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            writer.write("=== ApexPay Transaction Report ===");
            writer.newLine();
            writer.write("Generated: " + java.time.LocalDateTime.now());
            writer.newLine();
            writer.write("Total transactions: " + transactions.size());
            writer.newLine();
            writer.write("-".repeat(60));
            writer.newLine();

            for (Transaction txn : transactions) {
                writer.write(String.format("%s | %s | %.2f | %s",
                        txn.getTransactionId(),
                        txn.getClass().getSimpleName(),
                        txn.getAmount(),
                        txn.getTimestamp()));
                writer.newLine();
            }
        }

        System.out.printf("Exported %d transactions to %s%n", transactions.size(), filePath);
    }

    /** Runs a quick demo of export and import. */
    public static void demo() throws IOException {
        System.out.println("=== File I/O Demo ===");

        // Setup: create sample data
        AccountRepository accountRepo = new AccountRepository();
        TransactionRepository txnRepo = new TransactionRepository();
        BankService bankService = new BankService(accountRepo, txnRepo);

        bankService.createAccount("IO001", "ExportUser1");
        bankService.createAccount("IO002", "ExportUser2");
        bankService.deposit("IO001", 500, "IO-TXN-1");
        bankService.deposit("IO002", 1000, "IO-TXN-2");

        FilePersistenceService fileService = new FilePersistenceService(accountRepo, txnRepo);

        // Export to CSV
        Path csvPath = Path.of("apexpay_accounts_export.csv");
        fileService.exportAccountsToCsv(csvPath);

        // Export transactions to text
        Path txtPath = Path.of("apexpay_transactions_export.txt");
        fileService.exportTransactionsToText(txtPath);

        // Print file contents
        System.out.println("\n--- CSV Content ---");
        Files.readAllLines(csvPath).forEach(System.out::println);

        System.out.println("\n--- TXT Content ---");
        Files.readAllLines(txtPath).forEach(System.out::println);

        // Import from CSV into a fresh repo
        System.out.println("\n--- Import Test ---");
        AccountRepository freshRepo = new AccountRepository();
        TransactionRepository freshTxnRepo = new TransactionRepository();
        FilePersistenceService importer = new FilePersistenceService(freshRepo, freshTxnRepo);
        importer.importAccountsFromCsv(csvPath);

        System.out.println("Imported accounts:");
        freshRepo.findAll().forEach(Account::printAccount);

        // Cleanup
        Files.deleteIfExists(csvPath);
        Files.deleteIfExists(txtPath);
        System.out.println("\nCleanup complete.");
    }
}
