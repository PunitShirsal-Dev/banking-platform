package com.bank.complianceservice.infrastructure.messaging;

import com.bank.complianceservice.domain.model.ScreeningCase;
import com.bank.complianceservice.domain.port.ComplianceEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaComplianceEventPublisher implements ComplianceEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publishScreeningCompleted(ScreeningCase sc) {
        kafkaTemplate.send("compliance.screening.completed", sc.id().toString(), sc);
    }

    @Override
    public void publishScreeningEscalated(ScreeningCase sc) {
        kafkaTemplate.send("compliance.screening.escalated", sc.id().toString(), sc);
    }
}