package com.bank.complianceservice.application.command;

import lombok.Value;

@Value
public class ScreenCustomerCommand {
    String customerId;
    String firstName;
    String lastName;
    String taxId;
    String country;
    String idempotencyKey;
}