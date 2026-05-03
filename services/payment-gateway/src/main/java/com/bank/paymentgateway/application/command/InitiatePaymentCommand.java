package com.bank.paymentgateway.application.command;

import lombok.Value;
import java.math.BigDecimal;

@Value
public class InitiatePaymentCommand {
    String sourceAccountId;
    String beneficiaryName;
    String iban;
    String bic;
    String bankName;
    String countryCode;
    BigDecimal amount;
    String currency;
    String reference;
    String idempotencyKey;
}