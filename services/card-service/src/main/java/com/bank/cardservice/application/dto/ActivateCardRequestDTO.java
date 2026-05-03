package com.bank.cardservice.application.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ActivateCardRequestDTO {
    @NotBlank private String cardId;
}