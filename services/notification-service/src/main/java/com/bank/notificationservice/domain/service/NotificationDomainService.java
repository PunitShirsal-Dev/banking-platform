package com.bank.notificationservice.domain.service;

import com.bank.notificationservice.domain.model.*;
import com.bank.notificationservice.domain.port.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationDomainService {

    private final NotificationRepository repository;

    public Notification createAndStore(NotificationId id, NotificationType type, Recipient recipient,
                                       String subject, String body) {
        Notification notification = Notification.create(id, type, recipient, subject, body);
        repository.save(notification);
        return notification;
    }

    public Notification findById(NotificationId id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found"));
    }

    public void markSent(NotificationId id) {
        Notification n = findById(id);
        n.markSent();
        repository.save(n);
    }

    public void markFailed(NotificationId id) {
        Notification n = findById(id);
        n.markFailed();
        repository.save(n);
    }
}