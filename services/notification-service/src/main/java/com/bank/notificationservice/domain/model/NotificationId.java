package com.bank.notificationservice.domain.model;

import lombok.Value;
import java.util.UUID;

@Value(staticConstructor = "of")
public class NotificationId {
    UUID value;

    public static NotificationId random() {
        return new NotificationId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}