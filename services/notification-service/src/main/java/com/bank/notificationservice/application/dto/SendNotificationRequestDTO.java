package com.bank.notificationservice.application.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SendNotificationRequestDTO {
    @NotBlank private String type;        // EMAIL, SMS, PUSH
    private String email;
    private String phoneNumber;
    private String deviceToken;
    @NotBlank private String subject;
    @NotBlank private String body;
}