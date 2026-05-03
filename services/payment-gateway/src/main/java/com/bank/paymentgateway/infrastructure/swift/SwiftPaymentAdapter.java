package com.bank.paymentgateway.infrastructure.swift;

import com.bank.paymentgateway.domain.model.PaymentOrder;
import com.bank.paymentgateway.domain.port.ExternalPaymentAdapter;
import com.bank.paymentgateway.shared.JsonLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SwiftPaymentAdapter implements ExternalPaymentAdapter {

    private static final JsonLogger log = JsonLogger.of(SwiftPaymentAdapter.class);
    private final SwiftMessageBuilder messageBuilder;
    private final SwiftNetworkClient swiftNetworkClient;  // injected mock or real

    @Override
    public void send(PaymentOrder order) {
        if (order.paymentType() != com.bank.paymentgateway.domain.model.PaymentType.SWIFT) {
            throw new IllegalArgumentException("Swift adapter called for non-SWIFT payment");
        }
        String mt103 = messageBuilder.buildMT103(order);
        log.info("Building MT103 for payment {}", order.id());
        swiftNetworkClient.sendMT103(mt103, order);
        // here we assume success (the adapter is fire-and-forget, the caller marks the payment as SENT).
    }
}