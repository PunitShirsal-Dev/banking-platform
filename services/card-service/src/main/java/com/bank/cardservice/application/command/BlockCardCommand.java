package com.bank.cardservice.application.command;

import lombok.Value;

@Value
public class BlockCardCommand {
    String cardId;
    String idempotencyKey;
}
