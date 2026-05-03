package com.bank.transactionservice.infrastructure.web;

import com.bank.transactionservice.application.command.InitiateTransferCommand;
import com.bank.transactionservice.application.command.InitiateTransferHandler;
import com.bank.transactionservice.application.dto.TransferRequestDTO;
import com.bank.transactionservice.application.dto.TransferResponseDTO;
import com.bank.transactionservice.application.query.TransferStatusQuery;
import com.bank.transactionservice.domain.model.TransferAggregate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final InitiateTransferHandler initiateHandler;
    private final TransferStatusQuery statusQuery;

    @PostMapping
    public ResponseEntity<TransferResponseDTO> initiate(
            @RequestHeader("X-Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody TransferRequestDTO request) {

        InitiateTransferCommand command = new InitiateTransferCommand(
                request.getSourceAccountId(),
                request.getTargetAccountId(),
                request.getAmount(),
                request.getCurrency(),
                request.getDescription(),
                idempotencyKey
        );
        TransferAggregate transfer = initiateHandler.handle(command);
        return ResponseEntity.accepted().body(
                new TransferResponseDTO(transfer.id().toString(), transfer.state().name(), "Initiated")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransferResponseDTO> getTransfer(@PathVariable String id) {
        TransferAggregate transfer = statusQuery.getTransfer(id);
        return ResponseEntity.ok(
                new TransferResponseDTO(transfer.id().toString(), transfer.state().name(), "Current state")
        );
    }
}