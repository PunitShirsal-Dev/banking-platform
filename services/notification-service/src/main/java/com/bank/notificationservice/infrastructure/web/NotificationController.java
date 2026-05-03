package com.bank.notificationservice.infrastructure.web;

import com.bank.notificationservice.application.command.*;
import com.bank.notificationservice.application.dto.*;
import com.bank.notificationservice.application.query.NotificationStatusQuery;
import com.bank.notificationservice.domain.model.Notification;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final SendNotificationHandler sendHandler;
    private final NotificationStatusQuery statusQuery;

    @PostMapping
    public ResponseEntity<NotificationResponseDTO> send(
            @RequestHeader("X-Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody SendNotificationRequestDTO request) {

        SendNotificationCommand cmd = new SendNotificationCommand(
                idempotencyKey, request.getType(),
                request.getEmail(), request.getPhoneNumber(), request.getDeviceToken(),
                request.getSubject(), request.getBody()
        );
        Notification notification = sendHandler.handle(cmd);
        return ResponseEntity.accepted().body(toDto(notification));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponseDTO> getStatus(@PathVariable String id) {
        return ResponseEntity.ok(statusQuery.getStatus(id));
    }

    private NotificationResponseDTO toDto(Notification n) {
        return new NotificationResponseDTO(
                n.id().toString(), n.type().name(), n.status().name(), n.subject()
        );
    }
}