package com.bank.customerservice.domain.model;

import lombok.Value;

@Value
public class PersonalDetails {
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    String taxId;      // PII – encrypted at rest in DB
}