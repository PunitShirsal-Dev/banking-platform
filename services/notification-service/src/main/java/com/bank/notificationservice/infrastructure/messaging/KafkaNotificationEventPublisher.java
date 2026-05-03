package com.bank.notificationservice.infrastructure.messaging;

import com.bank.notificationservice.domain.model.Notification;
import com.bank.notificationservice.domain.port.NotificationEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaNotificationEventPublisher implements NotificationEventPublisher {

    private final KafkaTemplate<String, Object> kafka;

    @Override
    public void publishNotificationSent(Notification n) {
        kafka.send("notification.sent", n.id().toString(), n);
    }

    @Override
    public void publishNotificationFailed(Notification n) {
        kafka.send("notification.failed", n.id().toString(), n);
    }
}