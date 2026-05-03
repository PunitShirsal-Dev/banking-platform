package com.bank.notificationservice.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationResponseDTO {
    private String id;
    private String type;
    private String status;
    private String subject;
}