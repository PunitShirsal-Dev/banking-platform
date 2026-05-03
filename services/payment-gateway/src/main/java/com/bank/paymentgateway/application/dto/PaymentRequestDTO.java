package com.bank.paymentgateway.application.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class PaymentRequestDTO {
    @NotBlank private String sourceAccountId;
    @NotBlank private String beneficiaryName;
    private String iban;
    private String bic;
    private String bankName;
    @NotBlank private String countryCode;
    @Positive private BigDecimal amount;
    @NotBlank private String currency;
    private String reference;
}