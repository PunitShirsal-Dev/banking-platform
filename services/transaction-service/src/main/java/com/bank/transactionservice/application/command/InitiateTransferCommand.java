package com.bank.transactionservice.application.command;

import lombok.Value;
import java.math.BigDecimal;

@Value
public class InitiateTransferCommand {
    String sourceAccountId;
    String targetAccountId;
    BigDecimal amount;
    String currency;
    String description;
    String idempotencyKey;
}