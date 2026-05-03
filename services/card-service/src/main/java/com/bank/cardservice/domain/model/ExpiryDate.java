package com.bank.cardservice.domain.model;

import lombok.Value;
import java.time.YearMonth;

@Value(staticConstructor = "of")
public class ExpiryDate {
    YearMonth value;

    public static ExpiryDate fromNow(int monthsValid) {
        return new ExpiryDate(YearMonth.now().plusMonths(monthsValid));
    }
}