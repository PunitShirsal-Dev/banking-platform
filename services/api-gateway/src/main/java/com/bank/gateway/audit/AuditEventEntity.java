package com.bank.gateway.audit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "outbox")
@Getter
@Setter
@NoArgsConstructor
class AuditEventEntity {

    @Id
    private UUID id;
    private String aggregateId;       // will be set to "audit"
    private String eventType;         // "GatewayAccess"
    private String payload;           // JSON of AuditEvent
    private Instant createdAt;
    private boolean published;
}