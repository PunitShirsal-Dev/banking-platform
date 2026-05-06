package com.bank.gateway.filter;

import com.bank.gateway.audit.AuditEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RequestAuditFilter implements GatewayFilter {

    private final AuditEventService auditEventService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Asynchronously save audit event (fire-and-forget)
        auditEventService.saveAuditEvent(exchange);
        return chain.filter(exchange);
    }
}