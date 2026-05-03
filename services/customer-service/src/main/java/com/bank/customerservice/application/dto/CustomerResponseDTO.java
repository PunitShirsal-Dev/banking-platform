package com.bank.customerservice.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerResponseDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String status;
    private String kycStatus;
}