package com.bank.paymentgateway.application.query;

import com.bank.paymentgateway.application.dto.PaymentResponseDTO;
import com.bank.paymentgateway.domain.model.*;
import com.bank.paymentgateway.domain.port.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentStatusQuery {

    private final PaymentRepository repository;

    public PaymentResponseDTO getPayment(String paymentId) {
        PaymentOrder order = repository.findById(PaymentId.of(UUID.fromString(paymentId)))
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));
        return toDto(order);
    }

    private PaymentResponseDTO toDto(PaymentOrder order) {
        return new PaymentResponseDTO(
                order.id().toString(),
                order.sourceAccountId(),
                order.beneficiary().getName(),
                order.amount().getAmount().toString(),
                order.amount().getCurrency().getCurrencyCode(),
                order.paymentType().name(),
                order.status().name(),
                order.reference()
        );
    }
}