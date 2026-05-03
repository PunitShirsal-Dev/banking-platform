package com.bank.loanservice.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "loan")
@Getter
@Setter
@NoArgsConstructor
public class LoanEntity {

    @Id
    private UUID id;
    private String customerId;
    private String accountId;

    @Enumerated(EnumType.STRING)
    private LoanTypeEntity type;

    private BigDecimal principalAmount;
    private String currency;
    private int termMonths;
    private BigDecimal annualInterestRate;

    @Enumerated(EnumType.STRING)
    private LoanStatusEntity status;

    // Schedule stored as JSON text
    @Column(length = 4000)
    private String scheduleJson;

    private Instant appliedAt;
}