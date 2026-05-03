package com.bank.cardservice.domain.model;

public enum CardStatus {
    ISSUED,      // BankCard created but not yet activated
    ACTIVE,      // Ready for transactions
    BLOCKED,     // Temporarily blocked
    CLOSED       // Permanently closed
}