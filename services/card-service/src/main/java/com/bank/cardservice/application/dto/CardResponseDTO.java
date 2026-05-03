package com.bank.cardservice.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardResponseDTO {
    private String id;
    private String customerId;
    private String accountId;
    private String type;
    private String maskedCardNumber;
    private String expiryDate;
    private String status;
}