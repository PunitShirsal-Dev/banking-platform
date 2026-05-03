package com.bank.complianceservice.application.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ScreeningRequestDTO {
    @NotBlank private String customerId;
    @NotBlank private String firstName;
    @NotBlank private String lastName;
    @NotBlank private String taxId;
    @NotBlank private String country;
}