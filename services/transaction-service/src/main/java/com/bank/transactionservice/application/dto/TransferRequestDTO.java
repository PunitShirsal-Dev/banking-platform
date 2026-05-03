package com.bank.transactionservice.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.checkerframework.checker.index.qual.Positive;

import java.math.BigDecimal;

@Data
public class TransferRequestDTO {
    @NotBlank
    private String sourceAccountId;
    @NotBlank
    private String targetAccountId;
    @Positive
    private BigDecimal amount;
    @NotBlank
    private String currency;
    private String description;
}