package com.bank.paymentgateway.domain.model;

import lombok.Value;

@Value
public class Beneficiary {
    String name;
    String iban;           // International Bank Account Number
    String bic;            // SWIFT/BIC code
    String bankName;
    String countryCode;
}