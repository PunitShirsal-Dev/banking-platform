package com.bank.complianceservice.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class ScreeningResponseDTO {
    private String screeningId;
    private String customerName;
    private String result;
    private String caseStatus;
    private List<String> matchDetails;
}