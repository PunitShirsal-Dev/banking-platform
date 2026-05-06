package com.bank.reportingservice.domain.model;

import lombok.Value;

@Value
public class ReportResult {
    byte[] data;
    String format;   // CSV, PDF, JSON
    String fileName;
}