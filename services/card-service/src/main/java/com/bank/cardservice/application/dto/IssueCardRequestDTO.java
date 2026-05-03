package com.bank.cardservice.application.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class IssueCardRequestDTO {
    @NotBlank private String customerId;
    @NotBlank private String accountId;
    @NotBlank private String cardType;  // DEBIT or CREDIT
}