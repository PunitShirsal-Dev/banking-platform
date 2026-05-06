package com.bank.reportingservice.infrastructure.persistence;

import com.bank.reportingservice.domain.model.*;
import org.springframework.stereotype.Component;

@Component
public class ReportEntityMapper {

    public ReportEntity toEntity(Report report) {
        ReportEntity entity = new ReportEntity();
        entity.setId(report.id().getValue());
        entity.setCustomerId(report.definition().getCustomerId());
        entity.setType(ReportTypeEntity.valueOf(report.definition().getType().name()));
        entity.setStartDate(report.definition().getStartDate());
        entity.setEndDate(report.definition().getEndDate());
        entity.setStatus(ReportStatusEntity.valueOf(report.status().name()));
        if (report.result() != null) {
            entity.setResultData(report.result().getData());
            entity.setResultFormat(report.result().getFormat());
        }
        entity.setRequestedAt(report.requestedAt());
        entity.setCompletedAt(report.completedAt());
        return entity;
    }

    public Report toDomain(ReportEntity entity) {
        ReportId id = ReportId.of(entity.getId());
        ReportDefinition definition = new ReportDefinition(
                entity.getCustomerId(),
                ReportType.valueOf(entity.getType().name()),
                entity.getStartDate(),
                entity.getEndDate()
        );
        ReportStatus status = ReportStatus.valueOf(entity.getStatus().name());
        ReportResult result = null;
        if (entity.getResultData() != null) {
            result = new ReportResult(entity.getResultData(), entity.getResultFormat(), "report");
        }
        return Report.reconstitute(id, definition, status, result,
                entity.getRequestedAt(), entity.getCompletedAt());
    }
}