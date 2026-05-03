package com.bank.paymentgateway.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResponseDTO {
    private String id;
    private String sourceAccountId;
    private String beneficiaryName;
    private String amount;
    private String currency;
    private String type;
    private String status;
    private String reference;
}