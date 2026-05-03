package com.bank.transactionservice.domain.exception;

public class CustomTransactionServiceException extends RuntimeException {
    public CustomTransactionServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
