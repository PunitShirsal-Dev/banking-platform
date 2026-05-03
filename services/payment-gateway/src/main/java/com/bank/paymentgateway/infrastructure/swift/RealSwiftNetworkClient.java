package com.bank.paymentgateway.infrastructure.swift;

import com.bank.paymentgateway.domain.model.PaymentOrder;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class RealSwiftNetworkClient implements SwiftNetworkClient {

    @Override
    public void sendMT103(String mt103Message, PaymentOrder paymentOrder) {
        // Actual SWIFT integration – this would use JMS, MQ, or REST to SWIFT interface.
        // For demonstration, we throw an error to remind you to configure it.
        throw new UnsupportedOperationException(
                "Real SWIFT integration not configured. Please provide SWIFT interface credentials.");
    }
}