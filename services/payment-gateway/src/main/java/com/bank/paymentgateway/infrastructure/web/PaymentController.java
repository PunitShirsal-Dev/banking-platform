package com.bank.paymentgateway.infrastructure.web;

import com.bank.paymentgateway.application.command.*;
import com.bank.paymentgateway.application.dto.*;
import com.bank.paymentgateway.application.query.PaymentStatusQuery;
import com.bank.paymentgateway.domain.model.PaymentOrder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final InitiatePaymentHandler initiateHandler;
    private final PaymentStatusQuery statusQuery;

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> initiate(
            @RequestHeader("X-Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody PaymentRequestDTO request) {
        InitiatePaymentCommand cmd = new InitiatePaymentCommand(
                request.getSourceAccountId(), request.getBeneficiaryName(),
                request.getIban(), request.getBic(), request.getBankName(),
                request.getCountryCode(), request.getAmount(), request.getCurrency(),
                request.getReference(), idempotencyKey);
        PaymentOrder order = initiateHandler.handle(cmd);
        return ResponseEntity.ok(new PaymentResponseDTO(
                order.id().toString(), order.sourceAccountId(),
                order.beneficiary().getName(), order.amount().getAmount().toString(),
                order.amount().getCurrency().getCurrencyCode(), order.paymentType().name(),
                order.status().name(), order.reference()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> getPayment(@PathVariable String id) {
        return ResponseEntity.ok(statusQuery.getPayment(id));
    }
}