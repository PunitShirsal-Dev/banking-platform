package com.bank.notificationservice.infrastructure.client;

public interface EmailClient {
    void sendEmail(String to, String subject, String body);
}