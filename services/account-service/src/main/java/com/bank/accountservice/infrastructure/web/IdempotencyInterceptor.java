package com.bank.accountservice.infrastructure.web;

import com.bank.accountservice.infrastructure.redis.RedisIdempotencyStore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class IdempotencyInterceptor implements HandlerInterceptor {

    private final RedisIdempotencyStore idempotencyStore;

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {
        String key = request.getHeader("X-Idempotency-Key");
        if (key != null && idempotencyStore.isDuplicate(key)) {
            response.setStatus(409);
            response.getWriter().write("{\"error\":\"Duplicate request\"}");
            return false;
        }
        // Store the key for later removal on success? Not shown here.
        request.setAttribute("idempotencyKey", key);
        return true;
    }
}