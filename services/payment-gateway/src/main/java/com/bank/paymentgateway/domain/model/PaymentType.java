package com.bank.paymentgateway.domain.model;

public enum PaymentType {
    INTERNAL,       // within own bank
    SWIFT,          // international wire
    SEPA,           // Eurozone
    ACH,            // US domestic
    FEDWIRE
}