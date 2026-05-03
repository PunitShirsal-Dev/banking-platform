package com.bank.paymentgateway.domain.service;

import com.bank.paymentgateway.domain.model.Beneficiary;
import com.bank.paymentgateway.domain.model.PaymentType;
import org.springframework.stereotype.Component;

@Component
public class ExternalRoutingService {

    // Determine the payment type based on beneficiary details
    public PaymentType determinePaymentType(String sourceAccountId, Beneficiary beneficiary) {
        if (beneficiary.getIban() == null || beneficiary.getIban().isBlank() || sourceAccountId == null || sourceAccountId.isBlank()) {
            return PaymentType.INTERNAL; // internal transfer
        }
        String bic = beneficiary.getBic();
        if (bic != null && bic.length() >= 8) {
            // Simple heuristic: SWIFT codes often used for international
            return PaymentType.SWIFT;
        }
        return PaymentType.SWIFT; // default international
    }
}