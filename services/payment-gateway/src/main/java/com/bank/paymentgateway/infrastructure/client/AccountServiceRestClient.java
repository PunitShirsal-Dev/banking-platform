package com.bank.paymentgateway.infrastructure.client;

import com.bank.paymentgateway.domain.model.CustomPaymentServiceException;
import com.bank.paymentgateway.domain.model.PaymentOrder;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AccountServiceRestClient {

    private final RestTemplate restTemplate;

    @CircuitBreaker(name = "accountService", fallbackMethod = "debitFallback")
    public void debitAndCredit(PaymentOrder order) {
        // In a real scenario, you'd call debit on source account and credit on internal beneficiary account
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Idempotency-Key", java.util.UUID.randomUUID().toString());
        var debitRequest = new DebitRequest(order.sourceAccountId(), order.amount().getAmount(),
                order.amount().getCurrency().getCurrencyCode(), "Payment to " + order.beneficiary().getName());
        restTemplate.postForEntity("http://account-service/accounts/{id}/debit",
                new HttpEntity<>(debitRequest, headers), Void.class, order.sourceAccountId());
    }

    private void debitFallback(Throwable t) {
        throw new CustomPaymentServiceException("Account debit failed", t);
    }

    private record DebitRequest(String accountId, java.math.BigDecimal amount, String currency, String description) {}
}