-- V2__add_outbox_table.sql
CREATE TABLE outbox (
  id UUID PRIMARY KEY,
  aggregate_id UUID,
  event_type VARCHAR(255),
  payload JSONB
);