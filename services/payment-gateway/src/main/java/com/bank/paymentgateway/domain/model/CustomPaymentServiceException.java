package com.bank.paymentgateway.domain.model;

public class CustomPaymentServiceException extends RuntimeException {
    public CustomPaymentServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
