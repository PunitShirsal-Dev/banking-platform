package com.bank.transactionservice.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "transfer")
@Getter
@Setter
@NoArgsConstructor
public class TransferEntity {

    @Id
    private UUID id;

    private String sourceAccountId;
    private String targetAccountId;
    private BigDecimal amount;
    private String currency;

    private String description;

    @Enumerated(EnumType.STRING)
    private TransferStateEntity state;

    private Instant createdAt;
    private Instant updatedAt;
}