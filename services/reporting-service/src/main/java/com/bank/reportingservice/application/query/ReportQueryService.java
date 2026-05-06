package com.bank.reportingservice.application.query;

import com.bank.reportingservice.application.dto.ReportResponseDTO;
import com.bank.reportingservice.domain.model.*;
import com.bank.reportingservice.domain.port.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportQueryService {

    private final ReportRepository repository;

    public ReportResponseDTO getReport(String reportId) {
        Report r = repository.findById(ReportId.of(UUID.fromString(reportId)))
                .orElseThrow(() -> new IllegalArgumentException("Report not found"));
        return toDto(r);
    }

    private ReportResponseDTO toDto(Report r) {
        return new ReportResponseDTO(
                r.id().getValue().toString(),
                r.definition().getType().name(),
                r.status().name(),
                r.result() != null ? new String(r.result().getData()) : null
        );
    }
}