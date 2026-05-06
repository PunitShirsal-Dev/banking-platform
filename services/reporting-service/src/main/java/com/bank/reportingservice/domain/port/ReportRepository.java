package com.bank.reportingservice.domain.port;

import com.bank.reportingservice.domain.model.Report;
import com.bank.reportingservice.domain.model.ReportId;
import java.util.Optional;

public interface ReportRepository {
    void save(Report report);
    Optional<Report> findById(ReportId id);
}