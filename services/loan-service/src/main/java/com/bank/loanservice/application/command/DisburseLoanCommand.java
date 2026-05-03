package com.bank.loanservice.application.command;

import lombok.Value;

@Value
public class DisburseLoanCommand {
    String loanId;
    String idempotencyKey;
}