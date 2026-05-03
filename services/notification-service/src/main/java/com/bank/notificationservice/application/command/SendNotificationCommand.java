package com.bank.notificationservice.application.command;

import lombok.Value;

@Value
public class SendNotificationCommand {
    String idempotencyKey;
    String type;          // EMAIL, SMS, PUSH
    String email;
    String phoneNumber;
    String deviceToken;
    String subject;
    String body;
}