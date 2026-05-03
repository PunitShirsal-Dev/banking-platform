package com.bank.cardservice.application.command;

import lombok.Value;

@Value
public class ActivateCardCommand {
    String cardId;
    String idempotencyKey;
}