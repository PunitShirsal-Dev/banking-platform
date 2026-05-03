package com.bank.cardservice.application.command;

import lombok.Value;

@Value
public class IssueCardCommand {
    String customerId;
    String accountId;
    String cardType;       // DEBIT, CREDIT
    String idempotencyKey;
}