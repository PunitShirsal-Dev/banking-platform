package com.bank.accountservice.infrastructure.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
public class OutboxRelay {

    private static final Logger log = LoggerFactory.getLogger(OutboxRelay.class);

    private final JdbcTemplate jdbc;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${outbox.batch-size:100}")
    private int batchSize;

    public OutboxRelay(JdbcTemplate jdbc, KafkaTemplate<String, String> kafkaTemplate) {
        this.jdbc = jdbc;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedDelayString = "${outbox.poll-interval-ms:500}")
    @Transactional
    public void processOutbox() {
        // 1. Lock and load a batch of unpublished rows (oldest first)
        List<OutboxRow> rows = fetchUnpublishedRows();

        if (rows.isEmpty()) {
            log.debug("No outbox rows to process");
            return;
        }

        log.info("Processing {} outbox messages", rows.size());

        // 2. Publish each to Kafka, mark as published on success
        for (OutboxRow row : rows) {
            try {
                sendToKafka(row);
                markAsPublished(row.id());
            } catch (Exception e) {
                log.error("Failed to publish outbox row {}: {}", row.id(), e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        }
    }

    private List<OutboxRow> fetchUnpublishedRows() {
        // Use FOR UPDATE SKIP LOCKED so multiple pods can coexist
        String sql = """
                SELECT id, aggregate_id, event_type, payload
                FROM outbox
                WHERE published = false
                ORDER BY created_at ASC
                LIMIT ? FOR UPDATE SKIP LOCKED
                """;

        return jdbc.query(sql,
                (ResultSet rs, int rowNum) -> new OutboxRow(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("aggregate_id"),
                        rs.getString("event_type"),
                        rs.getString("payload")
                ),
                batchSize);
    }

    private void sendToKafka(OutboxRow row) throws ExecutionException, InterruptedException, TimeoutException {
        String topic = determineTopic(row.eventType());
        String key = row.aggregateId();

        CompletableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(topic, key, row.payload());

        // Wait for to send to complete (with a short timeout)
        future.get(5, TimeUnit.SECONDS);
        log.debug("Successfully sent outbox row {} to topic {}", row.id(), topic);
    }

    private void markAsPublished(UUID id) {
        jdbc.update("UPDATE outbox SET published = true WHERE id = ?", id.toString());
    }

    private String determineTopic(String eventType) {
        // Map event type names to Kafka topics (e.g., "AccountOpened" -> "account.opened")
        return switch (eventType) {
            case "AccountOpened" -> "account.opened";
            case "AccountDebited" -> "account.debited";
            case "AccountCredited" -> "account.credited";
            default -> "account.events"; // fallback
        };
    }

    private record OutboxRow(UUID id, String aggregateId, String eventType, String payload) {
    }
}