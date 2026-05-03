package com.bank.notificationservice.application.command;

import com.bank.notificationservice.domain.model.*;
import com.bank.notificationservice.domain.service.NotificationDomainService;
import com.bank.notificationservice.domain.port.NotificationEventPublisher;
import com.bank.notificationservice.infrastructure.client.EmailClient;
import com.bank.notificationservice.infrastructure.client.SmsClient;
import com.bank.notificationservice.shared.JsonLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SendNotificationHandler {

    private static final JsonLogger log = JsonLogger.of(SendNotificationHandler.class);
    private final NotificationDomainService domainService;
    private final NotificationEventPublisher eventPublisher;
    private final EmailClient emailClient;
    private final SmsClient smsClient;

    @Transactional
    public Notification handle(SendNotificationCommand command) {
        NotificationType type = NotificationType.valueOf(command.getType().toUpperCase());
        Recipient recipient = new Recipient(command.getEmail(), command.getPhoneNumber(), command.getDeviceToken());
        NotificationId id = NotificationId.random();

        // Store the notification as PENDING
        Notification notification = domainService.createAndStore(id, type, recipient, command.getSubject(), command.getBody());

        // Attempt to send
        try {
            sendOut(notification);
            domainService.markSent(id);
            eventPublisher.publishNotificationSent(notification);
            log.event("NotificationSent", notification);
        } catch (Exception e) {
            domainService.markFailed(id);
            eventPublisher.publishNotificationFailed(notification);
            log.event("NotificationFailed", notification);
            // Not re-throwing – the notification status is FAILED
        }
        return notification;
    }

    private void sendOut(Notification notification) {
        switch (notification.type()) {
            case EMAIL -> emailClient.sendEmail(
                    notification.recipient().getEmail(),
                    notification.subject(),
                    notification.body()
            );
            case SMS -> smsClient.sendSms(
                    notification.recipient().getEmail(),
                    notification.body()
            );
            // PUSH could also be implemented
            default -> throw new UnsupportedOperationException("Unsupported type: " + notification.type());
        }
    }
}