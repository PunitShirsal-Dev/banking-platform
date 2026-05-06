package com.bank.reportingservice.domain.service;

import com.bank.reportingservice.domain.model.*;
import com.bank.reportingservice.domain.port.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportDomainService {

    private final ReportRepository repository;

    public Report requestReport(ReportId id, ReportDefinition definition) {
        Report report = Report.request(id, definition);
        repository.save(report);
        return report;
    }

    public Report findById(ReportId id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Report not found"));
    }

    public void startGeneration(ReportId id) {
        Report report = findById(id);
        report.startGeneration();
        repository.save(report);
    }

    public void completeReport(ReportId id, ReportResult result) {
        Report report = findById(id);
        report.complete(result);
        repository.save(report);
    }

    public void failReport(ReportId id) {
        Report report = findById(id);
        report.fail();
        repository.save(report);
    }
}