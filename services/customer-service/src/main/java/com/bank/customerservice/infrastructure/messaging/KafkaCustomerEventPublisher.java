package com.bank.customerservice.infrastructure.messaging;

import com.bank.customerservice.domain.model.Customer;
import com.bank.customerservice.domain.port.CustomerEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaCustomerEventPublisher implements CustomerEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publishCustomerRegistered(Customer customer) {
        kafkaTemplate.send("customer.registered", customer.id().toString(), customer);
    }

    @Override
    public void publishCustomerActivated(Customer customer) {
        kafkaTemplate.send("customer.activated", customer.id().toString(), customer);
    }

    @Override
    public void publishCustomerBlocked(Customer customer) {
        kafkaTemplate.send("customer.blocked", customer.id().toString(), customer);
    }

    @Override
    public void publishKycVerified(Customer customer) {
        kafkaTemplate.send("customer.kyc.verified", customer.id().toString(), customer);
    }
}