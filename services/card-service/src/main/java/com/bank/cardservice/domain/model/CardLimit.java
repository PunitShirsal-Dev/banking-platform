package com.bank.cardservice.domain.model;

import lombok.Value;
import java.math.BigDecimal;
import java.util.Currency;

@Value
public class CardLimit {
    BigDecimal dailyLimit;
    BigDecimal transactionLimit;
    Currency currency;

    public static CardLimit defaultLimits() {
        return new CardLimit(
                new BigDecimal("5000.00"),
                new BigDecimal("1000.00"),
                Currency.getInstance("USD")
        );
    }
}