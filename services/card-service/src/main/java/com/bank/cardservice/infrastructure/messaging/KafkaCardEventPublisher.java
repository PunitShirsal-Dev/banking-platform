package com.bank.cardservice.infrastructure.messaging;

import com.bank.cardservice.domain.model.BankCard;
import com.bank.cardservice.domain.port.CardEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaCardEventPublisher implements CardEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publishCardIssued(BankCard bankCard) {
        kafkaTemplate.send("bankCard.issued", bankCard.id().toString(), bankCard);
    }
    @Override
    public void publishCardActivated(BankCard bankCard) {
        kafkaTemplate.send("bankCard.activated", bankCard.id().toString(), bankCard);
    }
    @Override
    public void publishCardBlocked(BankCard bankCard) {
        kafkaTemplate.send("bankCard.blocked", bankCard.id().toString(), bankCard);
    }
    @Override
    public void publishCardClosed(BankCard bankCard) {
        kafkaTemplate.send("bankCard.closed", bankCard.id().toString(), bankCard);
    }
}