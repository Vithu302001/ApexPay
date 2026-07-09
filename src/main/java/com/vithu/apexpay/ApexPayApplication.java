package com.vithu.apexpay;

import com.vithu.apexpay.repository.AccountRepository;
import com.vithu.apexpay.repository.TransactionRepository;
import com.vithu.apexpay.service.BankService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Spring Boot entry point for ApexPay.
 * <p>
 * The @Bean methods below make our core services available
 * as Spring-managed beans (Singleton scope by default).
 * </p>
 */
@SpringBootApplication
public class ApexPayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApexPayApplication.class, args);
    }

    @Bean
    public AccountRepository accountRepository() {
        return new AccountRepository();
    }

    @Bean
    public TransactionRepository transactionRepository() {
        return new TransactionRepository();
    }

    @Bean
    public BankService bankService(AccountRepository accountRepository,
                                   TransactionRepository transactionRepository) {
        return new BankService(accountRepository, transactionRepository);
    }
}
