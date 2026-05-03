package com.bank.loanservice.application.command;

import lombok.Value;

@Value
public class ApproveLoanCommand {
    String loanId;
    String idempotencyKey;
}