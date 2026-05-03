package com.bank.complianceservice.infrastructure.messaging;

import com.bank.complianceservice.application.command.ScreenCustomerCommand;
import com.bank.complianceservice.application.command.ScreenCustomerHandler;
import com.bank.complianceservice.shared.JsonLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerEventListener {

    private static final JsonLogger log = JsonLogger.of(CustomerEventListener.class);
    private final ScreenCustomerHandler screenHandler;

    @KafkaListener(topics = "customer.registered")
    public void onCustomerRegistered(Object event) {
        // In real life, parse the event (Avro/JSON) and extract needed fields
        log.info("Received customer.registered event");
        // Simulate a screening command with dummy data
        ScreenCustomerCommand cmd = new ScreenCustomerCommand(
                "customer-123", "John", "Doe", "T123", "US", "auto-event-key"
        );
        screenHandler.handle(cmd);
    }
}