package com.bank.paymentgateway.infrastructure.swift;

import com.bank.paymentgateway.domain.model.PaymentOrder;
import com.bank.paymentgateway.domain.port.ExternalPaymentAdapter;
import com.bank.paymentgateway.infrastructure.client.AccountServiceRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoutingExternalPaymentAdapter implements ExternalPaymentAdapter {

    private final SwiftPaymentAdapter swiftAdapter;
    private final AccountServiceRestClient accountServiceClient; // for internal

    @Override
    public void send(PaymentOrder order) {
        switch (order.paymentType()) {
            case INTERNAL -> accountServiceClient.debitAndCredit(order);
            case SWIFT -> swiftAdapter.send(order);
            // other types...
            default -> throw new UnsupportedOperationException("Unsupported payment type: " + order.paymentType());
        }
    }
}