package com.bank.notificationservice.domain.model;

import lombok.Value;

@Value
public class Recipient {
    String email;
    String phoneNumber;
    String deviceToken;
}