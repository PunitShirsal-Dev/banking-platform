package com.bank.paymentgateway.infrastructure.swift;

import com.bank.paymentgateway.domain.model.PaymentOrder;

public interface SwiftNetworkClient {
    /**
     * Sends an MT103 message to the SWIFT network.
     * @param mt103Message the complete MT103 message string
     * @param paymentOrder the associated payment order (for tracking)
     */
    void sendMT103(String mt103Message, PaymentOrder paymentOrder);
}