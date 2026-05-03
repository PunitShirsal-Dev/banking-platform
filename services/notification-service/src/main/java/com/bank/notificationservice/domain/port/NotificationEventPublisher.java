package com.bank.notificationservice.domain.port;

import com.bank.notificationservice.domain.model.Notification;

public interface NotificationEventPublisher {
    void publishNotificationSent(Notification notification);
    void publishNotificationFailed(Notification notification);
}