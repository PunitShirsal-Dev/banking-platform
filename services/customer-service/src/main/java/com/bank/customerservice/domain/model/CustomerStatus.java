package com.bank.customerservice.domain.model;

public enum CustomerStatus {
    PENDING,        // Registered but not yet fully verified
    ACTIVE,         // All checks passed
    BLOCKED,        // Complaints / risk
    CLOSED
}