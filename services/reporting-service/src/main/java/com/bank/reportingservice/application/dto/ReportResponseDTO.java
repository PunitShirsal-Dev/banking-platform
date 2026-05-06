package com.bank.reportingservice.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportResponseDTO {
    private String id;
    private String type;
    private String status;
    private String contentPreview;
}