package com.bank.gateway.audit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ServerWebExchange;

import java.security.Principal;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuditEventService {

    private final AuditEventRepository repository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public void saveAuditEvent(ServerWebExchange exchange) {
        String path = exchange.getRequest().getPath().value();
        String method = exchange.getRequest().getMethod().name();
        String principal = exchange.getPrincipal()
                .map(Principal::getName).blockOptional().orElse("anonymous");
        String idemKey = exchange.getRequest().getHeaders().getFirst("X-Idempotency-Key");

        AuditEvent event = new AuditEvent(UUID.randomUUID(), path, method, principal, idemKey, Instant.now());

        AuditEventEntity entity = new AuditEventEntity();
        entity.setId(event.getId());
        entity.setAggregateId("audit");
        entity.setEventType("GatewayAccess");
        try {
            entity.setPayload(objectMapper.writeValueAsString(event));
        } catch (JsonProcessingException e) {
            entity.setPayload("{}");
        }
        entity.setCreatedAt(Instant.now());
        entity.setPublished(false);
        repository.save(entity);
    }
}