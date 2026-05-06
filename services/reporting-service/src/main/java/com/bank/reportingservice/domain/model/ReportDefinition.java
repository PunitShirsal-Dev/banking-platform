package com.bank.reportingservice.domain.model;

import lombok.Value;
import java.time.LocalDate;

@Value
public class ReportDefinition {
    String customerId;
    ReportType type;
    LocalDate startDate;
    LocalDate endDate;
}