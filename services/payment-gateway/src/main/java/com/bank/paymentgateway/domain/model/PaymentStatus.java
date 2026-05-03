package com.bank.paymentgateway.domain.model;

public enum PaymentStatus {
    INITIATED,
    VALIDATED,
    PROCESSING,
    SENT,
    COMPLETED,
    FAILED
}