package com.bank.notificationservice.infrastructure.messaging;

import com.bank.notificationservice.application.command.*;
import com.bank.notificationservice.shared.JsonLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DomainEventListener {

    private static final JsonLogger log = JsonLogger.of(DomainEventListener.class);
    private final SendNotificationHandler sendHandler;

    @KafkaListener(topics = "customer.registered")
    public void onCustomerRegistered(Object event) {
        log.info("Received customer.registered event – would send welcome email");
        // In real code, parse the event to get customer details,
        // then call sendHandler with a constructed command.
    }

    @KafkaListener(topics = "transaction.completed")
    public void onTransactionCompleted(Object event) {
        log.info("Received transaction.completed event – would send confirmation");
    }

    // Add more listeners as needed
}