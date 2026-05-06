package com.bank.reportingservice.application.command;

import lombok.Value;
import java.time.LocalDate;

@Value
public class GenerateReportCommand {
    String customerId;
    String type;           // e.g., DAILY_BALANCE
    LocalDate startDate;
    LocalDate endDate;
    String idempotencyKey;
}