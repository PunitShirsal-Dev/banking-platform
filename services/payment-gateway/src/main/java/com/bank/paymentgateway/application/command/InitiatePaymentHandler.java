package com.bank.paymentgateway.application.command;

import com.bank.paymentgateway.domain.model.*;
import com.bank.paymentgateway.domain.service.PaymentDomainService;
import com.bank.paymentgateway.domain.port.PaymentEventPublisher;
import com.bank.paymentgateway.shared.JsonLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InitiatePaymentHandler {

    private static final JsonLogger log = JsonLogger.of(InitiatePaymentHandler.class);
    private final PaymentDomainService domainService;
    private final PaymentEventPublisher eventPublisher;

    @Transactional
    public PaymentOrder handle(InitiatePaymentCommand command) {
        PaymentId id = PaymentId.random();
        Beneficiary beneficiary = new Beneficiary(
                command.getBeneficiaryName(),
                command.getIban(),
                command.getBic(),
                command.getBankName(),
                command.getCountryCode()
        );
        Money amount = Money.of(command.getAmount(), command.getCurrency());
        PaymentOrder order = domainService.initiatePayment(
                id, command.getSourceAccountId(), beneficiary, amount, command.getReference());
        eventPublisher.publishPaymentInitiated(order);
        log.event("PaymentInitiated", order);
        return order;
    }
}