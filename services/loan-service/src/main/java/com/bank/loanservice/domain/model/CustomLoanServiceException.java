package com.bank.loanservice.domain.model;

public class CustomLoanServiceException extends RuntimeException {
    public CustomLoanServiceException(String message, Throwable t) {
        super(message, t);
    }
}
