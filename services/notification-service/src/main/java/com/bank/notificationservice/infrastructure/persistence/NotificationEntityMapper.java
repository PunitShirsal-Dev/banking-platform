package com.bank.notificationservice.infrastructure.persistence;

import com.bank.notificationservice.domain.model.*;
import org.springframework.stereotype.Component;

@Component
public class NotificationEntityMapper {

    public NotificationEntity toEntity(Notification n) {
        NotificationEntity e = new NotificationEntity();
        e.setId(n.id().getValue());
        e.setType(NotificationTypeEntity.valueOf(n.type().name()));
        e.setEmail(n.recipient().getEmail());
        e.setPhoneNumber(n.recipient().getPhoneNumber());
        e.setDeviceToken(n.recipient().getDeviceToken());
        e.setSubject(n.subject());
        e.setBody(n.body());
        e.setStatus(NotificationStatusEntity.valueOf(n.status().name()));
        e.setCreatedAt(n.createdAt());
        return e;
    }

    public Notification toDomain(NotificationEntity e) {
        Recipient recipient = new Recipient(e.getEmail(), e.getPhoneNumber(), e.getDeviceToken());
        return Notification.reconstitute(
                NotificationId.of(e.getId()),
                NotificationType.valueOf(e.getType().name()),
                recipient,
                e.getSubject(),
                e.getBody(),
                NotificationStatus.valueOf(e.getStatus().name()),
                e.getCreatedAt()
        );
    }
}