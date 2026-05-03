package com.bank.loanservice.domain.model;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class LoanApplication {
    String customerId;
    String accountId;      // account to disburse into
    LoanType type;
    Money principal;
    int termMonths;
    BigDecimal annualInterestRate;
}