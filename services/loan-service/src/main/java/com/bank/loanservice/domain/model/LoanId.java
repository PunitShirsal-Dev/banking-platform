package com.bank.loanservice.domain.model;

import lombok.Value;
import java.util.UUID;

@Value(staticConstructor = "of")
public class LoanId {
    UUID value;

    public static LoanId random() {
        return new LoanId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}