package com.bank.notificationservice.infrastructure.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SmtpEmailClient implements EmailClient {

    private static final Logger log = LoggerFactory.getLogger(SmtpEmailClient.class);

    @Override
    public void sendEmail(String to, String subject, String body) {
        // In production, integrate with JavaMailSender or AWS SES
        log.info("Sending EMAIL to {} subject '{}'", to, subject);
        // Simulate success
    }
}