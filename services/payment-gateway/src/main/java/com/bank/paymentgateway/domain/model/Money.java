package com.bank.paymentgateway.domain.model;

import lombok.Value;
import java.math.BigDecimal;
import java.util.Currency;

@Value(staticConstructor = "of")
public class Money {
    BigDecimal amount;
    Currency currency;

    public static Money of(BigDecimal amount, String currencyCode) {
        return new Money(amount, Currency.getInstance(currencyCode));
    }

    public Money add(Money other) {
        assertSameCurrency(other);
        return new Money(amount.add(other.amount), currency);
    }

    private void assertSameCurrency(Money other) {
        if (!currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currency mismatch");
        }
    }
}