package com.bank.transactionservice.domain.model;

import lombok.Value;
import java.util.UUID;

@Value(staticConstructor = "of")
public class AccountId {
    UUID value;
}