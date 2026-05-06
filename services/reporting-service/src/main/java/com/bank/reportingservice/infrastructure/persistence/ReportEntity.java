package com.bank.reportingservice.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "report")
@Getter
@Setter
@NoArgsConstructor
public class ReportEntity {

    @Id
    private UUID id;
    private String customerId;
    @Enumerated(EnumType.STRING)
    private ReportTypeEntity type;
    private LocalDate startDate;
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private ReportStatusEntity status;
    // Result stored as BLOB or reference to file
    @Lob
    private byte[] resultData;
    private String resultFormat;
    private Instant requestedAt;
    private Instant completedAt;
}