package com.bank.accountservice.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountResponseDTO {
    private String id;
    private String customerId;
    private String type;
    private String status;
    private String balance;
    private String currency;
}