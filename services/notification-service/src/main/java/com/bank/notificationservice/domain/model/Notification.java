package com.bank.notificationservice.domain.model;

import lombok.Getter;
import lombok.experimental.Accessors;
import java.time.Instant;

@Getter
@Accessors(fluent = true)
public class Notification {
    private NotificationId id;
    private NotificationType type;
    private Recipient recipient;
    private String subject;
    private String body;
    private NotificationStatus status;
    private Instant createdAt;

    private Notification() {}

    public static Notification create(NotificationId id, NotificationType type, Recipient recipient,
                                      String subject, String body) {
        Notification notification = new Notification();
        notification.id = id;
        notification.type = type;
        notification.recipient = recipient;
        notification.subject = subject;
        notification.body = body;
        notification.status = NotificationStatus.PENDING;
        notification.createdAt = Instant.now();
        return notification;
    }

    public void markSent() {
        status = NotificationStatus.SENT;
    }

    public void markFailed() {
        status = NotificationStatus.FAILED;
    }

    // Reconstitution
    public static Notification reconstitute(NotificationId id, NotificationType type, Recipient recipient,
                                            String subject, String body, NotificationStatus status, Instant createdAt) {
        Notification n = new Notification();
        n.id = id;
        n.type = type;
        n.recipient = recipient;
        n.subject = subject;
        n.body = body;
        n.status = status;
        n.createdAt = createdAt;
        return n;
    }
}