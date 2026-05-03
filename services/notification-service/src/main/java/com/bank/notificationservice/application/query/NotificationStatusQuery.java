package com.bank.notificationservice.application.query;

import com.bank.notificationservice.application.dto.NotificationResponseDTO;
import com.bank.notificationservice.domain.model.*;
import com.bank.notificationservice.domain.port.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationStatusQuery {

    private final NotificationRepository repository;

    public NotificationResponseDTO getStatus(String notificationId) {
        Notification n = repository.findById(NotificationId.of(UUID.fromString(notificationId)))
                .orElseThrow(() -> new IllegalArgumentException("Notification not found"));
        return new NotificationResponseDTO(
                n.id().toString(),
                n.type().name(),
                n.status().name(),
                n.subject()
        );
    }
}