package com.bank.transactionservice.domain.model;

import lombok.Value;
import java.util.UUID;

@Value(staticConstructor = "of")
public class TransactionId {
    UUID value;

    public static TransactionId random() {
        return new TransactionId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}