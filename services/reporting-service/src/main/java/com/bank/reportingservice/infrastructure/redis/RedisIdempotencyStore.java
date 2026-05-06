package com.bank.reportingservice.infrastructure.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisIdempotencyStore {

    private final StringRedisTemplate redis;

    public boolean isDuplicate(String key) {
        Boolean set = redis.opsForValue().setIfAbsent(key, "1", Duration.ofHours(24));
        return set == null || !set;
    }
}