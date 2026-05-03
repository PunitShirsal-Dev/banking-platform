package com.bank.paymentgateway.domain.model;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@Accessors(fluent = true)
public class PaymentOrder {
    private PaymentId id;
    private String sourceAccountId;   // internal account debited
    private Beneficiary beneficiary;
    private Money amount;
    private PaymentType paymentType;
    private PaymentStatus status;
    private String reference;
    private Instant createdAt;

    private PaymentOrder() {
    }

    public static PaymentOrder initiate(PaymentId id, String sourceAccountId, Beneficiary beneficiary,
                                        Money amount, PaymentType paymentType, String reference) {
        PaymentOrder order = new PaymentOrder();
        order.id = id;
        order.sourceAccountId = sourceAccountId;
        order.beneficiary = beneficiary;
        order.amount = amount;
        order.paymentType = paymentType;
        order.status = PaymentStatus.INITIATED;
        order.reference = reference;
        order.createdAt = Instant.now();
        return order;
    }

    public void validate() {
        if (status != PaymentStatus.INITIATED) throw new IllegalStateException("Cannot validate");
        status = PaymentStatus.VALIDATED;
    }

    public void startProcessing() {
        status = PaymentStatus.PROCESSING;
    }

    public void markSent() {
        status = PaymentStatus.SENT;
    }

    public void complete() {
        status = PaymentStatus.COMPLETED;
    }

    public void fail() {
        status = PaymentStatus.FAILED;
    }

    public record GenericPayment(String sourceAccountId, String reference, Instant createdAt) {
    }

    // Reconstitution
    public static PaymentOrder reconstitute(PaymentId id, Beneficiary beneficiary,
                                            Money amount, PaymentType paymentType, PaymentStatus status,
                                            GenericPayment genericPayment) {
        PaymentOrder order = new PaymentOrder();
        order.id = id;
        order.sourceAccountId = genericPayment.sourceAccountId;
        order.beneficiary = beneficiary;
        order.amount = amount;
        order.paymentType = paymentType;
        order.status = status;
        order.reference = genericPayment.reference;
        order.createdAt = genericPayment.createdAt;
        return order;
    }
}