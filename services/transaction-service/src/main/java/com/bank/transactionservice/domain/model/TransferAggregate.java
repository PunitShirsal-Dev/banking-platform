package com.bank.transactionservice.domain.model;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@Accessors(fluent = true)
public class TransferAggregate {
    private TransactionId id;
    private TransferDetails details;
    private TransferState state;
    private Instant createdAt;
    private Instant updatedAt;

    private TransferAggregate() {}

    public static TransferAggregate initiate(TransactionId id, TransferDetails details) {
        TransferAggregate transfer = new TransferAggregate();
        transfer.id = id;
        transfer.details = details;
        transfer.state = TransferState.INITIATED;
        transfer.createdAt = Instant.now();
        transfer.updatedAt = transfer.createdAt;
        return transfer;
    }

    public void markSourceDebitPending() {
        if (state != TransferState.INITIATED) throw new IllegalStateException("Invalid state transition");
        state = TransferState.SOURCE_DEBIT_PENDING;
        touch();
    }

    public void markSourceDebitApproved() {
        if (state != TransferState.SOURCE_DEBIT_PENDING) throw new IllegalStateException("Invalid transition");
        state = TransferState.SOURCE_DEBIT_APPROVED;
        touch();
    }

    public void markTargetCreditPending() {
        if (state != TransferState.SOURCE_DEBIT_APPROVED) throw new IllegalStateException("Invalid transition");
        state = TransferState.TARGET_CREDIT_PENDING;
        touch();
    }

    public void markTargetCreditApproved() {
        if (state != TransferState.TARGET_CREDIT_PENDING) throw new IllegalStateException("Invalid transition");
        state = TransferState.TARGET_CREDIT_APPROVED;
        touch();
    }

    public void markCompleted() {
        if (state != TransferState.TARGET_CREDIT_APPROVED) throw new IllegalStateException("Invalid transition");
        state = TransferState.COMPLETED;
        touch();
    }

    // Compensating actions
    public void markCompensationSourceCreditPending() {
        state = TransferState.COMPENSATION_SOURCE_CREDIT_PENDING;
        touch();
    }

    public void markCompensationSourceCreditApproved() {
        state = TransferState.COMPENSATION_SOURCE_CREDIT_APPROVED;
        touch();
    }

    public void markFailed() {
        state = TransferState.FAILED;
        touch();
    }

    // Reconstitution (package‑private setters needed or a public static factory)
    public static TransferAggregate reconstitute(TransactionId id, TransferDetails details, TransferState state,
                                                 Instant createdAt, Instant updatedAt) {
        TransferAggregate t = new TransferAggregate();
        t.id = id;
        t.details = details;
        t.state = state;
        t.createdAt = createdAt;
        t.updatedAt = updatedAt;
        return t;
    }

    private void touch() { this.updatedAt = Instant.now(); }
}