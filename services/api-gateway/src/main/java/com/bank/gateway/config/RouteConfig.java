package com.bank.gateway.config;

import com.bank.gateway.filter.IdempotencyPropagationFilter;
import com.bank.gateway.filter.RequestAuditFilter;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {
    private final RedisRateLimiter redisRateLimiter;

    public RouteConfig(RedisRateLimiter redisRateLimiter) {
        this.redisRateLimiter = redisRateLimiter;
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder,
                                           IdempotencyPropagationFilter idempotencyFilter,
                                           RequestAuditFilter auditFilter) {
        return builder.routes()
                .route("customer-service", r -> r.path("/api/customers/**")
                        .filters(f -> f
                                .filter(idempotencyFilter)
                                .filter(auditFilter)
                                .requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter))) // configure later
                        .uri("lb://customer-service"))
                .route("account-service", r -> r.path("/api/accounts/**")
                        .filters(f -> f.filter(idempotencyFilter).filter(auditFilter))
                        .uri("lb://account-service"))
                .route("transaction-service", r -> r.path("/api/transfers/**")
                        .filters(f -> f.filter(idempotencyFilter).filter(auditFilter))
                        .uri("lb://transaction-service"))
                .route("card-service", r -> r.path("/api/cards/**")
                        .filters(f -> f.filter(idempotencyFilter).filter(auditFilter))
                        .uri("lb://card-service"))
                .route("compliance-service", r -> r.path("/api/compliance/**")
                        .filters(f -> f.filter(idempotencyFilter).filter(auditFilter))
                        .uri("lb://compliance-service"))
                .route("loan-service", r -> r.path("/api/loans/**")
                        .filters(f -> f.filter(idempotencyFilter).filter(auditFilter))
                        .uri("lb://loan-service"))
                .route("notification-service", r -> r.path("/api/notifications/**")
                        .filters(f -> f.filter(idempotencyFilter).filter(auditFilter))
                        .uri("lb://notification-service"))
                .route("reporting-service", r -> r.path("/api/reports/**")
                        .filters(f -> f.filter(idempotencyFilter).filter(auditFilter))
                        .uri("lb://reporting-service"))
                .build();
    }
}