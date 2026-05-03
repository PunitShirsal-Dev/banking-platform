CREATE TABLE outbox (
                        id UUID PRIMARY KEY,
                        aggregate_id VARCHAR(255) NOT NULL,
                        event_type VARCHAR(255) NOT NULL,
                        payload JSONB NOT NULL,
                        created_at TIMESTAMP NOT NULL,
                        published BOOLEAN DEFAULT FALSE
);