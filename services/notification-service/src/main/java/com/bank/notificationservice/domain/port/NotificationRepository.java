package com.bank.notificationservice.domain.port;

import com.bank.notificationservice.domain.model.Notification;
import com.bank.notificationservice.domain.model.NotificationId;
import java.util.Optional;

public interface NotificationRepository {
    void save(Notification notification);
    Optional<Notification> findById(NotificationId id);
}