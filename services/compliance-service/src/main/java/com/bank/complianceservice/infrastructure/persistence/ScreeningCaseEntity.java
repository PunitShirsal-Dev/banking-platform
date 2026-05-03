package com.bank.complianceservice.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "screening_case")
@Getter
@Setter
@NoArgsConstructor
public class ScreeningCaseEntity {

    @Id
    private UUID id;
    private String customerId;
    private String firstName;
    private String lastName;
    private String taxId;
    private String country;

    @Enumerated(EnumType.STRING)
    private ScreeningResultEntity result;

    // Matches stored as JSON in text column
    @Column(length = 4000)
    private String matchesJson;

    @Enumerated(EnumType.STRING)
    private CaseStatusEntity caseStatus;

    private Instant screenedAt;
}