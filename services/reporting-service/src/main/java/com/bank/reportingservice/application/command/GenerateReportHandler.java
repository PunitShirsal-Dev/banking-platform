package com.bank.reportingservice.application.command;

import com.bank.reportingservice.domain.model.*;
import com.bank.reportingservice.domain.service.ReportDomainService;
import com.bank.reportingservice.domain.port.ReportEventPublisher;
import com.bank.reportingservice.shared.JsonLogger;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GenerateReportHandler {

    private static final JsonLogger log = JsonLogger.of(GenerateReportHandler.class);
    private final ReportDomainService domainService;
    private final ReportEventPublisher eventPublisher;

    @Resource
    private GenerateReportHandler reportHandler;

    @Transactional
    public Report handle(GenerateReportCommand command) {
        ReportId id = ReportId.random();
        ReportType type = ReportType.valueOf(command.getType().toUpperCase());
        ReportDefinition definition = new ReportDefinition(
                command.getCustomerId(), type, command.getStartDate(), command.getEndDate()
        );
        Report report = domainService.requestReport(id, definition);
        // Start async generation (simplified: ideally we'd use a dedicated worker)
        reportHandler.generateAsync(report);
        log.event("ReportRequested", report);
        return report;
    }

    @Async
    public void generateAsync(Report report) {
        try {
            domainService.startGeneration(report.id());
            // Simulate report generation – in reality, query materialized views or data lake
            byte[] data = ("Report for " + report.definition().getCustomerId()).getBytes();
            ReportResult result = new ReportResult(data, "CSV", "report.csv");
            domainService.completeReport(report.id(), result);
            eventPublisher.publishReportCompleted(report);
        } catch (Exception e) {
            domainService.failReport(report.id());
            eventPublisher.publishReportFailed(report);
        }
    }
}