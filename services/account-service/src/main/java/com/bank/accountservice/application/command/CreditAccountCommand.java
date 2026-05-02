package com.bank.accountservice.application.command;

import java.math.BigDecimal;

public record CreditAccountCommand(String accountId, BigDecimal amount, String currency, String description,
                                   String idempotencyKey) {
}