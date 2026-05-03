package com.bank.transactionservice.domain.port;

import com.bank.transactionservice.domain.model.TransactionId;
import com.bank.transactionservice.domain.model.TransferAggregate;

import java.util.Optional;

public interface TransferRepository {
    void save(TransferAggregate transfer);
    Optional<TransferAggregate> findById(TransactionId id);
}