package com.bank.transactionservice.domain.port;

import com.bank.transactionservice.domain.model.TransferAggregate;

public interface EventPublisher {
    void transferInitiated(TransferAggregate transfer);
    void transferCompleted(TransferAggregate transfer);
    void transferFailed(TransferAggregate transfer, String reason);
}