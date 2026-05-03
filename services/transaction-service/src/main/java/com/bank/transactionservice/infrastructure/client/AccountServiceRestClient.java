package com.bank.transactionservice.infrastructure.client;

import com.bank.transactionservice.domain.exception.CustomTransactionServiceException;
import com.bank.transactionservice.domain.model.AccountId;
import com.bank.transactionservice.domain.model.Money;
import com.bank.transactionservice.domain.port.AccountServiceClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AccountServiceRestClient implements AccountServiceClient {

    private final RestTemplate restTemplate;

    @Override
    @CircuitBreaker(name = "accountService", fallbackMethod = "debitFallback")
    public void debit(AccountId accountId, Money amount, String description) {
        HttpHeaders headers = new HttpHeaders();
        // Add idempotency key, auth headers if needed
        headers.set("X-Idempotency-Key", java.util.UUID.randomUUID().toString());

        var request = new DebitRequest(accountId.getValue().toString(), amount.getAmount(), amount.getCurrency().getCurrencyCode(), description);
        restTemplate.postForEntity("http://account-service/accounts/{id}/debit", new HttpEntity<>(request, headers), Void.class, accountId.getValue().toString());
    }

    @Override
    @CircuitBreaker(name = "accountService", fallbackMethod = "creditFallback")
    public void credit(AccountId accountId, Money amount, String description) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Idempotency-Key", java.util.UUID.randomUUID().toString());
        var request = new DebitRequest(accountId.getValue().toString(), amount.getAmount(), amount.getCurrency().getCurrencyCode(), description);
        restTemplate.postForEntity("http://account-service/accounts/{id}/credit", new HttpEntity<>(request, headers), Void.class, accountId.getValue().toString());
    }

    // Fallback methods (for circuit breaker)
    private void debitFallback(Throwable t) {
        throw new CustomTransactionServiceException("Account service debit failed", t);
    }

    private void creditFallback(Throwable t) {
        throw new CustomTransactionServiceException("Account service credit failed", t);
    }

    // DTO (same as account service's DebitRequestDTO)
    private record DebitRequest(String accountId, java.math.BigDecimal amount, String currency, String description) {}
}