package com.vithu.apexpay.controller;

import com.vithu.apexpay.dto.TransactionResponse;
import com.vithu.apexpay.service.BankService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final BankService bankService;

    public TransactionController(BankService bankService) {
        this.bankService = bankService;
    }

    /** GET /api/transactions — list all transactions */
    @GetMapping
    public List<TransactionResponse> getAllTransactions() {
        return bankService.getAllTransactions().stream()
                .map(TransactionResponse::from)
                .collect(Collectors.toList());
    }

    /** GET /api/transactions?accountId=XXX — filter by account */
    @GetMapping(params = "accountId")
    public List<TransactionResponse> getTransactionsByAccount(@RequestParam String accountId) {
        return bankService.getTransactionsByAccount(accountId).stream()
                .map(TransactionResponse::from)
                .collect(Collectors.toList());
    }

    /** GET /api/transactions/largest — get the largest transaction */
    @GetMapping("/largest")
    public TransactionResponse getLargestTransaction() {
        return bankService.getLargestTransaction()
                .map(TransactionResponse::from)
                .orElse(null);
    }
}
