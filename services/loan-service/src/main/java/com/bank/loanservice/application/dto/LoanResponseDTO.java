package com.bank.loanservice.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoanResponseDTO {
    private String id;
    private String customerId;
    private String type;
    private String principal;
    private String currency;
    private String status;
    private int termMonths;
}