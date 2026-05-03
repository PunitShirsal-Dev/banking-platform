package com.bank.paymentgateway.domain.model;

import lombok.Value;
import java.util.UUID;

@Value(staticConstructor = "of")
public class PaymentId {
    UUID value;

    public static PaymentId random() {
        return new PaymentId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}