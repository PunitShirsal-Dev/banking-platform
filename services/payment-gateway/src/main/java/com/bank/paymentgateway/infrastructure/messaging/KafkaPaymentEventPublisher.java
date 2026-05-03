package com.bank.paymentgateway.infrastructure.messaging;

import com.bank.paymentgateway.domain.model.PaymentOrder;
import com.bank.paymentgateway.domain.port.PaymentEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaPaymentEventPublisher implements PaymentEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publishPaymentInitiated(PaymentOrder order) {
        kafkaTemplate.send("payment.initiated", order.id().toString(), order);
    }

    @Override
    public void publishPaymentSent(PaymentOrder order) {
        kafkaTemplate.send("payment.sent", order.id().toString(), order);
    }

    @Override
    public void publishPaymentCompleted(PaymentOrder order) {
        kafkaTemplate.send("payment.completed", order.id().toString(), order);
    }

    @Override
    public void publishPaymentFailed(PaymentOrder order) {
        kafkaTemplate.send("payment.failed", order.id().toString(), order);
    }
}