package com.vithu.apexpay;

import com.vithu.apexpay.exception.AccountNotFoundException;
import com.vithu.apexpay.exception.InsufficientFundsException;
import com.vithu.apexpay.model.Account;
import com.vithu.apexpay.model.Transaction;
import com.vithu.apexpay.repository.AccountRepository;
import com.vithu.apexpay.repository.TransactionRepository;
import com.vithu.apexpay.service.BankService;
import com.vithu.apexpay.service.ConcurrencySimulation;
import com.vithu.apexpay.service.FilePersistenceService;

import java.util.Optional;

public class Main {

    public static void main(String[] args) throws Exception {
        // Part 1: Optional<T> + Generics
        demoOptionalAndGenerics();

        System.out.println("\n" + "=" .repeat(70) + "\n");

        // Part 2: Concurrency Simulation (ExecutorService)
        ConcurrencySimulation.demo();

        System.out.println("\n" + "=" .repeat(70) + "\n");

        // Part 3: File I/O
        FilePersistenceService.demo();
    }

    static void demoOptionalAndGenerics() {
        System.out.println("=== PART 1: Optional<T> + Generics Demo ===");

        AccountRepository accountRepository = new AccountRepository();
        TransactionRepository transactionRepository = new TransactionRepository();
        BankService bankService = new BankService(accountRepository, transactionRepository);

        bankService.createAccount("ACC001", "Alice");
        bankService.createAccount("ACC002", "Bob");
        bankService.createAccount("ACC003", "Charlie");

        System.out.println("\n--- Optional Patterns ---");

        // Optional.ifPresent
        accountRepository.findById("ACC001")
                .ifPresent(acc -> System.out.println("ifPresent: " + acc.getOwnerName()));

        // Optional.isPresent
        System.out.println("ACC999 exists? " + accountRepository.findById("ACC999").isPresent());

        // Optional.orElse
        Account fallback = accountRepository.findById("ACC999")
                .orElse(new Account("DEFAULT", "Fallback"));
        System.out.println("orElse: " + fallback.getOwnerName());

        // Optional.orElseThrow
        accountRepository.findById("ACC001")
                .orElseThrow(() -> new AccountNotFoundException("ACC001"));
        System.out.println("orElseThrow: ACC001 found");

        // Optional.map + orElse
        String owner = accountRepository.findById("ACC999")
                .map(Account::getOwnerName)
                .orElse("UNKNOWN");
        System.out.println("map+orElse: " + owner);

        // Transactions
        bankService.deposit("ACC001", 1000, "TXN001");
        bankService.deposit("ACC002", 500, "TXN002");
        bankService.deposit("ACC003", 2000, "TXN003");
        bankService.deposit("ACC001", 500, "TXN004");
        bankService.withdraw("ACC002", 100, "TXN005");
        bankService.transfer("ACC001", "ACC003", 300, "TXN006");

        // Exceptions
        try {
            bankService.deposit("ACC999", 1000, "TXN007");
        } catch (AccountNotFoundException e) {
            System.out.println("Caught: " + e.getMessage());
        }
        try {
            bankService.withdraw("ACC002", 999999, "TXN009");
        } catch (InsufficientFundsException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        // Largest transaction (Optional.ifPresentOrElse)
        System.out.println("\n--- Largest Transaction ---");
        bankService.getLargestTransaction().ifPresentOrElse(
                t -> System.out.println("Largest: " + t),
                () -> System.out.println("No transactions.")
        );

        // Generics
        System.out.println("\n--- Generics: BaseRepository<T, ID> ---");
        System.out.println("AccountRepository     implements BaseRepository<Account, String>");
        System.out.println("TransactionRepository implements BaseRepository<Transaction, String>");
    }
}
