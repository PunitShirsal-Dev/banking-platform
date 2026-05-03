package com.bank.transactionservice.infrastructure.messaging;

import com.bank.transactionservice.domain.model.TransactionId;
import com.bank.transactionservice.domain.model.TransferAggregate;
import com.bank.transactionservice.domain.model.TransferState;
import com.bank.transactionservice.domain.port.TransferRepository;
import com.bank.transactionservice.domain.service.TransferSagaService;
import com.bank.transactionservice.shared.JsonLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SagaEventListener {

    private static final JsonLogger log = JsonLogger.of(SagaEventListener.class);

    private final TransferRepository transferRepository;
    private final TransferSagaService sagaService;

    @KafkaListener(topics = "account.debited")
    @Transactional
    public void onSourceDebited(String messageKey, Object event) {
        // Extract transfer ID from event or use message key
        TransactionId transferId = TransactionId.of(java.util.UUID.fromString(messageKey));
        TransferAggregate transfer = transferRepository.findById(transferId)
                .orElseThrow(() -> new IllegalStateException("Transfer not found"));
        if (transfer.state() == TransferState.SOURCE_DEBIT_PENDING) {
            transfer.markSourceDebitApproved();
            transferRepository.save(transfer);
            sagaService.processSagaStep(transfer); // triggers target credit
        }
    }

    @KafkaListener(topics = "account.credited")
    @Transactional
    public void onTargetCredited(String messageKey, Object event) {
        TransactionId transferId = TransactionId.of(java.util.UUID.fromString(messageKey));
        TransferAggregate transfer = transferRepository.findById(transferId)
                .orElseThrow(() -> new IllegalStateException("Transfer not found"));
        if (transfer.state() == TransferState.TARGET_CREDIT_PENDING) {
            transfer.markTargetCreditApproved();
            transferRepository.save(transfer);
            sagaService.processSagaStep(transfer); // completes
        } else if (transfer.state() == TransferState.COMPENSATION_SOURCE_CREDIT_PENDING) {
            sagaService.processSagaStep(transfer); // marks compensated and failed
        }
    }

    // Optional: handle account.debit.failed or timeout for compensation
}
