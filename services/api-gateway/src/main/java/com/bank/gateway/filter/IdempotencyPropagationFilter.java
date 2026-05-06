package com.bank.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class IdempotencyPropagationFilter implements GatewayFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Ensure the X-Idempotency-Key header (if present) is forwarded downstream
        String idempotencyKey = exchange.getRequest().getHeaders().getFirst("X-Idempotency-Key");
        if (idempotencyKey != null) {
            exchange = exchange.mutate()
                    .request(r -> r.headers(headers -> headers.set("X-Idempotency-Key", idempotencyKey)))
                    .build();
        }
        return chain.filter(exchange);
    }
}