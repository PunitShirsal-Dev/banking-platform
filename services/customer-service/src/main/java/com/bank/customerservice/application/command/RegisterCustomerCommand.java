package com.bank.customerservice.application.command;

import lombok.Value;

@Value
public class RegisterCustomerCommand {
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    String taxId;
    String street;
    String city;
    String state;
    String zipCode;
    String country;
    String idempotencyKey;
}