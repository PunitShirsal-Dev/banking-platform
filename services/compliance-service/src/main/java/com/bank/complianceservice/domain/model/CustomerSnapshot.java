package com.bank.complianceservice.domain.model;

import lombok.Value;

@Value
public class CustomerSnapshot {
    String customerId;
    String firstName;
    String lastName;
    String taxId;
    String country;
}