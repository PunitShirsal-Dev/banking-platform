package com.bank.reportingservice.domain.port;

import com.bank.reportingservice.domain.model.Report;

public interface ReportEventPublisher {
    void publishReportCompleted(Report report);
    void publishReportFailed(Report report);
}