package com.bank.accountservice.application.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreditRequestDTO {
    @NotBlank
    private String accountId;
    @Positive
    private BigDecimal amount;
    @NotBlank
    private String currency;
    private String description;
}