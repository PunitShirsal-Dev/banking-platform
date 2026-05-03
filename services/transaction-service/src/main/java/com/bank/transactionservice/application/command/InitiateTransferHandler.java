package com.bank.transactionservice.application.command;

import com.bank.transactionservice.domain.model.AccountId;
import com.bank.transactionservice.domain.model.Money;
import com.bank.transactionservice.domain.model.TransferAggregate;
import com.bank.transactionservice.domain.model.TransferDetails;
import com.bank.transactionservice.domain.service.TransferSagaService;
import com.bank.transactionservice.shared.JsonLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InitiateTransferHandler {

    private static final JsonLogger log = JsonLogger.of(InitiateTransferHandler.class);

    private final TransferSagaService sagaService;

    @Transactional
    public TransferAggregate handle(InitiateTransferCommand command) {
        AccountId source = AccountId.of(UUID.fromString(command.getSourceAccountId()));
        AccountId target = AccountId.of(UUID.fromString(command.getTargetAccountId()));
        Money amount = Money.of(command.getAmount(), command.getCurrency());

        TransferDetails details = new TransferDetails(source, target, amount, command.getDescription());
        TransferAggregate transfer = sagaService.initiateTransfer(details);

        log.event("TransferInitiated", transfer);
        // Start the saga (asynchronously or synchronously – here we assume a separate scheduler or event triggers the next step)
        // In a real system, a Saga orchestrator would pick this up via event or scheduled job.
        return transfer;
    }
}
