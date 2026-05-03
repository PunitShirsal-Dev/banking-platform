CREATE TABLE transfer (
                          id UUID PRIMARY KEY,
                          source_account_id VARCHAR(36) NOT NULL,
                          target_account_id VARCHAR(36) NOT NULL,
                          amount DECIMAL(19,4) NOT NULL,
                          currency VARCHAR(3) NOT NULL,
                          description TEXT,
                          state VARCHAR(50) NOT NULL,
                          created_at TIMESTAMP NOT NULL,
                          updated_at TIMESTAMP NOT NULL
);