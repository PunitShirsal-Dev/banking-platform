package com.bank.paymentgateway.infrastructure.swift;

import com.bank.paymentgateway.domain.model.PaymentOrder;
import com.bank.paymentgateway.shared.JsonLogger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!prod")  // Use a real implementation in production
public class MockSwiftNetworkClient implements SwiftNetworkClient {

    private static final JsonLogger log = JsonLogger.of(MockSwiftNetworkClient.class);

    @Override
    public void sendMT103(String mt103Message, PaymentOrder paymentOrder) {
        // Simulate SWIFT network call
        log.info("Mock SWIFT: Sending MT103 for payment {}:\n{}", paymentOrder.id(), mt103Message);
        // In reality, you would connect to SWIFT Alliance, SAG, or SWIFT Cloud.
        // Here we just log and simulate success.
    }
}