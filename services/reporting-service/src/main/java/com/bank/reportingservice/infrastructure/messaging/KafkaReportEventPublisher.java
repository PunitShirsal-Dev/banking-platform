package com.bank.reportingservice.infrastructure.messaging;

import com.bank.reportingservice.domain.model.Report;
import com.bank.reportingservice.domain.port.ReportEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaReportEventPublisher implements ReportEventPublisher {

    private final KafkaTemplate<String, Object> kafka;

    @Override
    public void publishReportCompleted(Report report) {
        kafka.send("report.completed", report.id().toString(), report);
    }

    @Override
    public void publishReportFailed(Report report) {
        kafka.send("report.failed", report.id().toString(), report);
    }
}