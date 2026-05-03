package com.bank.paymentgateway.domain.port;

import com.bank.paymentgateway.domain.model.PaymentOrder;

public interface PaymentEventPublisher {
    void publishPaymentInitiated(PaymentOrder order);
    void publishPaymentSent(PaymentOrder order);
    void publishPaymentCompleted(PaymentOrder order);
    void publishPaymentFailed(PaymentOrder order);
}