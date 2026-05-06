CREATE TABLE report (
                        id UUID PRIMARY KEY,
                        customer_id VARCHAR(36) NOT NULL,
                        type VARCHAR(30) NOT NULL,
                        start_date DATE,
                        end_date DATE,
                        status VARCHAR(20) NOT NULL,
                        result_data BYTEA,
                        result_format VARCHAR(10),
                        requested_at TIMESTAMP NOT NULL,
                        completed_at TIMESTAMP
);

-- Example materialized view for daily balances (filled by event consumer)
CREATE TABLE daily_balances (
                                account_id UUID NOT NULL,
                                balance DECIMAL(19,4) NOT NULL,
                                date DATE NOT NULL,
                                PRIMARY KEY (account_id, date)
);