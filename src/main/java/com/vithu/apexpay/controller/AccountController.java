package com.vithu.apexpay.controller;

import com.vithu.apexpay.dto.AccountResponse;
import com.vithu.apexpay.dto.CreateAccountRequest;
import com.vithu.apexpay.dto.DepositRequest;
import com.vithu.apexpay.dto.TransferRequest;
import com.vithu.apexpay.dto.WithdrawRequest;
import com.vithu.apexpay.exception.AccountNotFoundException;
import com.vithu.apexpay.exception.InsufficientFundsException;
import com.vithu.apexpay.model.Account;
import com.vithu.apexpay.service.BankService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final BankService bankService;

    public AccountController(BankService bankService) {
        this.bankService = bankService;
    }

    /** POST /api/accounts — create a new account (ID auto-generated) */
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        String accountId = generateAccountId();
        bankService.createAccount(accountId, request.getOwnerName());
        Account account = bankService.findAccount(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
        return ResponseEntity.status(HttpStatus.CREATED).body(AccountResponse.from(account));
    }

    /** GET /api/accounts — list all accounts */
    @GetMapping
    public List<AccountResponse> getAllAccounts() {
        return bankService.getAllAccounts().stream()
                .map(AccountResponse::from)
                .collect(Collectors.toList());
    }

    /** GET /api/accounts/{id} — get account by ID */
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable String id) {
        return bankService.findAccount(id)
                .map(account -> ResponseEntity.ok(AccountResponse.from(account)))
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    /** POST /api/accounts/{id}/deposit — deposit money */
    @PostMapping("/{id}/deposit")
    public ResponseEntity<AccountResponse> deposit(@PathVariable String id,
                                                   @Valid @RequestBody DepositRequest request) {
        bankService.deposit(id, request.getAmount(), generateTxnId());
        Account account = bankService.findAccount(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
        return ResponseEntity.ok(AccountResponse.from(account));
    }

    /** POST /api/accounts/{id}/withdraw — withdraw money */
    @PostMapping("/{id}/withdraw")
    public ResponseEntity<AccountResponse> withdraw(@PathVariable String id,
                                                    @Valid @RequestBody WithdrawRequest request) {
        bankService.withdraw(id, request.getAmount(), generateTxnId());
        Account account = bankService.findAccount(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
        return ResponseEntity.ok(AccountResponse.from(account));
    }

    /** POST /api/accounts/{id}/transfer — transfer to another account */
    @PostMapping("/{id}/transfer")
    public ResponseEntity<AccountResponse> transfer(@PathVariable String id,
                                                    @Valid @RequestBody TransferRequest request) {
        bankService.transfer(id, request.getToAccountId(), request.getAmount(), generateTxnId());
        Account account = bankService.findAccount(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
        return ResponseEntity.ok(AccountResponse.from(account));
    }

    /** DELETE /api/accounts/{id} — delete an account */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable String id) {
        if (!bankService.accountExists(id)) {
            throw new AccountNotFoundException(id);
        }
        bankService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    /** Exception handlers for this controller */
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(AccountNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<Map<String, String>> handleInsufficientFunds(InsufficientFundsException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
    }

    private static String generateAccountId() {
        return "ACC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private static String generateTxnId() {
        return "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
