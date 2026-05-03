package com.bank.transactionservice.infrastructure.messaging;

import com.bank.transactionservice.domain.model.TransferAggregate;
import com.bank.transactionservice.domain.port.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaEventPublisher implements EventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void transferInitiated(TransferAggregate transfer) {
        kafkaTemplate.send("transaction.initiated", transfer.id().toString(), transfer);
    }

    @Override
    public void transferCompleted(TransferAggregate transfer) {
        kafkaTemplate.send("transaction.completed", transfer.id().toString(), transfer);
    }

    @Override
    public void transferFailed(TransferAggregate transfer, String reason) {
        kafkaTemplate.send("transaction.failed", transfer.id().toString(), transfer);
    }
}