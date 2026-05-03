package com.bank.customerservice.application.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterCustomerRequestDTO {
    @NotBlank private String firstName;
    @NotBlank private String lastName;
    @Email @NotBlank private String email;
    @NotBlank private String phoneNumber;
    @NotBlank private String taxId;
    @NotBlank private String street;
    @NotBlank private String city;
    @NotBlank private String state;
    @NotBlank private String zipCode;
    @NotBlank private String country;
}