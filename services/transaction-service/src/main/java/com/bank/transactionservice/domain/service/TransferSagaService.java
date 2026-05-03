package com.bank.transactionservice.domain.service;

import com.bank.transactionservice.domain.model.TransactionId;
import com.bank.transactionservice.domain.model.TransferAggregate;
import com.bank.transactionservice.domain.model.TransferDetails;
import com.bank.transactionservice.domain.port.AccountServiceClient;
import com.bank.transactionservice.domain.port.EventPublisher;
import com.bank.transactionservice.domain.port.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransferSagaService {

    private final TransferRepository transferRepository;
    private final AccountServiceClient accountServiceClient;
    private final EventPublisher eventPublisher;

    public TransferAggregate initiateTransfer(TransferDetails details) {
        TransferAggregate transfer = TransferAggregate.initiate(TransactionId.random(), details);
        transferRepository.save(transfer);
        eventPublisher.transferInitiated(transfer);
        return transfer;
    }

    // Called by the saga orchestrator after InitiateTransferHandler
    public void processSagaStep(TransferAggregate transfer) {
        switch (transfer.state()) {
            case INITIATED -> executeSourceDebit(transfer);
            case SOURCE_DEBIT_APPROVED -> executeTargetCredit(transfer);
            case TARGET_CREDIT_APPROVED -> completeTransfer(transfer);
            case COMPENSATION_SOURCE_CREDIT_PENDING -> executeCompensationCredit(transfer);
            // Other states are terminal or wait for events
            default -> throw new IllegalStateException("No action for state: " + transfer.state());
        }
    }

    private void executeSourceDebit(TransferAggregate transfer) {
        transfer.markSourceDebitPending();
        transferRepository.save(transfer);
        accountServiceClient.debit(transfer.details().getSourceAccountId(),
                transfer.details().getAmount(),
                "Transfer to " + transfer.details().getTargetAccountId());
        // Response will come asynchronously via SagaEventListener
    }

    private void executeTargetCredit(TransferAggregate transfer) {
        transfer.markTargetCreditPending();
        transferRepository.save(transfer);
        accountServiceClient.credit(transfer.details().getTargetAccountId(),
                transfer.details().getAmount(),
                "Transfer from " + transfer.details().getSourceAccountId());
    }

    private void completeTransfer(TransferAggregate transfer) {
        transfer.markCompleted();
        transferRepository.save(transfer);
        eventPublisher.transferCompleted(transfer);
    }

    // Compensating action when target credit fails
    public void compensateSourceDebit(TransferAggregate transfer) {
        transfer.markCompensationSourceCreditPending();
        transferRepository.save(transfer);
        accountServiceClient.credit(transfer.details().getSourceAccountId(),
                transfer.details().getAmount(),
                "Compensation for failed transfer to " + transfer.details().getTargetAccountId());
    }

    private void executeCompensationCredit(TransferAggregate transfer) {
        transfer.markCompensationSourceCreditApproved();
        transfer.markFailed();
        transferRepository.save(transfer);
        eventPublisher.transferFailed(transfer, "Compensation applied");
    }
}
