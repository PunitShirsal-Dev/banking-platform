package com.bank.accountservice.infrastructure.messaging;

import com.bank.accountservice.domain.model.Account;
import com.bank.accountservice.domain.port.AccountEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaAccountEventPublisher implements AccountEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publishAccountOpened(Account account) {
        kafkaTemplate.send("account.opened", account.id().toString(), account);
    }

    @Override
    public void publishAccountDebited(Account account) {
        kafkaTemplate.send("account.debited", account.id().toString(), account);
    }

    @Override
    public void publishAccountCredited(Account account) {
        kafkaTemplate.send("account.credited", account.id().toString(), account);
    }
}