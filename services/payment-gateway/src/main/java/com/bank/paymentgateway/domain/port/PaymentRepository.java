package com.bank.paymentgateway.domain.port;

import com.bank.paymentgateway.domain.model.PaymentOrder;
import com.bank.paymentgateway.domain.model.PaymentId;
import java.util.Optional;

public interface PaymentRepository {
    void save(PaymentOrder paymentOrder);
    Optional<PaymentOrder> findById(PaymentId id);
}