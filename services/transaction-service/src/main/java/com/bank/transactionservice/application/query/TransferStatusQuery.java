package com.bank.transactionservice.application.query;

import com.bank.transactionservice.domain.model.TransactionId;
import com.bank.transactionservice.domain.model.TransferAggregate;
import com.bank.transactionservice.domain.port.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferStatusQuery {

    private final TransferRepository transferRepository;

    public TransferAggregate getTransfer(String id) {
        return transferRepository.findById(TransactionId.of(java.util.UUID.fromString(id)))
                .orElseThrow(() -> new IllegalArgumentException("Transfer not found"));
    }
}
