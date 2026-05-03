package com.bank.loanservice.application.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class LoanApplicationRequestDTO {
    @NotBlank private String customerId;
    @NotBlank private String accountId;
    @NotBlank private String loanType;      // PERSONAL, MORTGAGE, AUTO
    @Positive private BigDecimal principalAmount;
    @NotBlank private String currency;
    @Min(1) @Max(360) private int termMonths;
    @Positive private BigDecimal annualInterestRate;
}