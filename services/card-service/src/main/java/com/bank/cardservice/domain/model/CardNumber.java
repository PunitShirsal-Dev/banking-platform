package com.bank.cardservice.domain.model;

import lombok.Value;

@Value
public class CardNumber {
    String full;     // encrypted or tokenised when stored
    String masked;   // e.g., "****1234"

    public static CardNumber of(String full) {
        String masked = "****" + full.substring(full.length() - 4);
        return new CardNumber(full, masked);
    }
}