package com.bank.reportingservice.domain.model;

import lombok.Getter;
import lombok.experimental.Accessors;
import java.time.Instant;

@Getter
@Accessors(fluent = true)
public class Report {
    private ReportId id;
    private ReportDefinition definition;
    private ReportStatus status;
    private ReportResult result;   // null until COMPLETED
    private Instant requestedAt;
    private Instant completedAt;

    private Report() {}

    public static Report request(ReportId id, ReportDefinition definition) {
        Report report = new Report();
        report.id = id;
        report.definition = definition;
        report.status = ReportStatus.PENDING;
        report.requestedAt = Instant.now();
        return report;
    }

    public void startGeneration() {
        if (status != ReportStatus.PENDING) throw new IllegalStateException("Can only start from PENDING");
        status = ReportStatus.GENERATING;
    }

    public void complete(ReportResult result) {
        status = ReportStatus.COMPLETED;
        this.result = result;
        completedAt = Instant.now();
    }

    public void fail() {
        status = ReportStatus.FAILED;
    }

    // Reconstitution
    public static Report reconstitute(ReportId id, ReportDefinition definition, ReportStatus status,
                                      ReportResult result, Instant requestedAt, Instant completedAt) {
        Report report = new Report();
        report.id = id;
        report.definition = definition;
        report.status = status;
        report.result = result;
        report.requestedAt = requestedAt;
        report.completedAt = completedAt;
        return report;
    }
}