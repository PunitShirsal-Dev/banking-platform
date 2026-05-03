package com.bank.cardservice.infrastructure.messaging;

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
        List<OutboxRow> rows = fetchUnpublishedRows();
        if (rows.isEmpty()) {
            log.debug("No outbox rows to process");
            return;
        }
        log.info("Processing {} outbox messages", rows.size());
        for (OutboxRow row : rows) {
            try {
                sendToKafka(row);
                markAsPublished(row.id());
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                log.error("Failed to publish outbox row {}: {}", row.id(), e.getMessage(), e);
            }
        }
    }

    private List<OutboxRow> fetchUnpublishedRows() {
        String sql = "SELECT id, aggregate_id, event_type, payload FROM outbox WHERE published = false ORDER BY created_at ASC LIMIT ? FOR UPDATE SKIP LOCKED";
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
        CompletableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(topic, row.aggregateId(), row.payload());
        future.get(5, TimeUnit.SECONDS);
    }

    private void markAsPublished(UUID id) {
        jdbc.update("UPDATE outbox SET published = true WHERE id = ?", id.toString());
    }

    private String determineTopic(String eventType) {
        return switch (eventType) {
            case "CardIssued"    -> "card.issued";
            case "CardActivated" -> "card.activated";
            case "CardBlocked"   -> "card.blocked";
            case "CardClosed"    -> "card.closed";
            default              -> "card.events";
        };
    }

    private record OutboxRow(UUID id, String aggregateId, String eventType, String payload) {}
}