package com.bank.loanservice.infrastructure.client;

import com.bank.loanservice.domain.model.CustomLoanServiceException;
import com.bank.loanservice.domain.model.Money;
import com.bank.loanservice.domain.port.AccountServiceClient;
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
    @CircuitBreaker(name = "accountService", fallbackMethod = "creditFallback")
    public void creditAccount(String accountId, Money amount, String description) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Idempotency-Key", java.util.UUID.randomUUID().toString());
        var request = new CreditRequest(accountId, amount.getAmount(), amount.getCurrency().getCurrencyCode(), description);
        restTemplate.postForEntity("http://account-service/accounts/{id}/credit",
                new HttpEntity<>(request, headers), Void.class, accountId);
    }

    private void creditFallback(Throwable t) throws CustomLoanServiceException {
        throw new CustomLoanServiceException("Account service credit failed", t);
    }

    private record CreditRequest(String accountId, java.math.BigDecimal amount, String currency, String description) {}
}