package com.bank.transactionservice.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransferResponseDTO {
    private String transactionId;
    private String status;
    private String message;
}