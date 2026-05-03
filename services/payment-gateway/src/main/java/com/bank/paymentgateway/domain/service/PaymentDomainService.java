package com.bank.paymentgateway.domain.service;

import com.bank.paymentgateway.domain.model.*;
import com.bank.paymentgateway.domain.port.PaymentRepository;
import com.bank.paymentgateway.domain.port.ExternalPaymentAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentDomainService {

    private final PaymentRepository paymentRepository;
    private final ExternalRoutingService routingService;
    private final ExternalPaymentAdapter externalPaymentAdapter;

    public PaymentOrder initiatePayment(PaymentId id, String sourceAccountId,
                                        Beneficiary beneficiary, Money amount, String reference) {
        PaymentType paymentType = routingService.determinePaymentType(sourceAccountId, beneficiary);
        PaymentOrder order = PaymentOrder.initiate(id, sourceAccountId, beneficiary, amount, paymentType, reference);
        paymentRepository.save(order);
        return order;
    }

    public PaymentOrder findById(PaymentId id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payment order not found"));
    }

    public PaymentOrder processPayment(PaymentId id) {
        PaymentOrder order = findById(id);
        // Validate and begin processing
        order.validate();
        order.startProcessing();
        paymentRepository.save(order);
        // Delegate to the appropriate external adapter (could be internal via account service, SWIFT, etc.)
        externalPaymentAdapter.send(order);
        order.markSent(); // adapter may be asynchronous; this is simplified
        paymentRepository.save(order);
        return order;
    }
}