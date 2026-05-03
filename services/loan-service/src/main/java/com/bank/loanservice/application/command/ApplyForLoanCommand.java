package com.bank.loanservice.application.command;

import lombok.Value;
import java.math.BigDecimal;

@Value
public class ApplyForLoanCommand {
    String customerId;
    String accountId;
    String loanType;
    BigDecimal principalAmount;
    String currency;
    int termMonths;
    BigDecimal annualInterestRate;
    String idempotencyKey;
}