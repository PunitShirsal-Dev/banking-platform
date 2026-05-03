package com.bank.notificationservice.infrastructure.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MockSmsClient implements SmsClient {

    private static final Logger log = LoggerFactory.getLogger(MockSmsClient.class);

    @Override
    public void sendSms(String phoneNumber, String message) {
        log.info("Sending SMS to {} message: {}", phoneNumber, message);
    }
}