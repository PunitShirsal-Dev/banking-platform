package com.bank.reportingservice.infrastructure.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Slf4j
@Component
@RequiredArgsConstructor
public class CoreBankingEventConsumer {

    public static final String DUMMY = "dummy";
    private final JdbcTemplate jdbc;

    @KafkaListener(topics = "account.opened")
    public void onAccountOpened(String message) {
        // Parse message and update daily_balances materialized view
        jdbc.update("INSERT INTO daily_balances (account_id, balance, date) VALUES (?, ?, current_date) ON CONFLICT (account_id, date) DO NOTHING",
                extractAccountId(message), extractBalance(message));
    }

    // Simplified extraction – in real code use Avro deserializer
    private String extractAccountId(String msg) {
        log.debug(msg);
        return DUMMY;
    }
    private java.math.BigDecimal extractBalance(String msg) {
        log.debug(msg);
        return java.math.BigDecimal.ZERO;
    }
}