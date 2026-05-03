package com.bank.notificationservice.infrastructure.client;

public interface SmsClient {
    void sendSms(String phoneNumber, String message);
}