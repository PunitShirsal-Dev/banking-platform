package com.bank.reportingservice.application.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ReportRequestDTO {
    @NotBlank private String customerId;
    @NotBlank private String type;         // DAILY_BALANCE etc.
    @NotNull private LocalDate startDate;
    @NotNull private LocalDate endDate;
}