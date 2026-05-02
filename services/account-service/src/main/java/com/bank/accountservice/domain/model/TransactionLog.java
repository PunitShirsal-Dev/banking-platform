package com.bank.accountservice.domain.model;

import lombok.Value;
import java.time.Instant;

@Value
public class TransactionLog {
    TransactionType type;
    Money amount;
    String description;
    Instant timestamp;

    // Primary constructor for new logs (always now)
    public TransactionLog(TransactionType type, Money amount, String description) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.timestamp = Instant.now();
    }

    // Public all‑args constructor for reconstitution from storage
    public TransactionLog(TransactionType type, Money amount, String description, Instant timestamp) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.timestamp = timestamp;
    }
}