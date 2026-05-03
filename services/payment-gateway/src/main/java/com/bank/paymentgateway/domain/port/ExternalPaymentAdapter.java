package com.bank.paymentgateway.domain.port;

import com.bank.paymentgateway.domain.model.PaymentOrder;

public interface ExternalPaymentAdapter {
    void send(PaymentOrder order);
}