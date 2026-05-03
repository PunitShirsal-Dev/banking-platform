package com.bank.notificationservice.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "notification")
@Getter
@Setter
@NoArgsConstructor
public class NotificationEntity {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private NotificationTypeEntity type;

    private String email;
    private String phoneNumber;
    private String deviceToken;

    private String subject;
    private String body;

    @Enumerated(EnumType.STRING)
    private NotificationStatusEntity status;

    private Instant createdAt;
}