package com.bank.reportingservice.domain.model;

import lombok.Value;
import java.util.UUID;

@Value(staticConstructor = "of")
public class ReportId {
    UUID value;

    public static ReportId random() {
        return new ReportId(UUID.randomUUID());
    }
}