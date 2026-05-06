package com.bank.gateway.audit;

import lombok.Value;
import java.time.Instant;
import java.util.UUID;

@Value
public class AuditEvent {
    UUID id;
    String requestPath;
    String method;
    String principalName;
    String idempotencyKey;
    Instant timestamp;
}