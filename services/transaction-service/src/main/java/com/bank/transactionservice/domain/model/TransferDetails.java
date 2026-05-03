package com.bank.transactionservice.domain.model;

import lombok.Value;

@Value
public class TransferDetails {
    AccountId sourceAccountId;
    AccountId targetAccountId;
    Money amount;
    String description;
}