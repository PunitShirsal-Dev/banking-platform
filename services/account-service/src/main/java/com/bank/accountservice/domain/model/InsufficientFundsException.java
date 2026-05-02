package com.bank.accountservice.domain.model;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(AccountId accountId, Money amount) {
        super("Account " + accountId + " has insufficient funds to debit " + amount);
    }
}