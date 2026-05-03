CREATE TABLE bankCard (
                      id UUID PRIMARY KEY,
                      customer_id VARCHAR(36) NOT NULL,
                      account_id VARCHAR(36) NOT NULL,
                      type VARCHAR(10) NOT NULL,
                      card_number_full VARCHAR(255) NOT NULL,
                      card_number_masked VARCHAR(19) NOT NULL,
                      expiry_date VARCHAR(7) NOT NULL,
                      cvv_hash VARCHAR(255),
                      status VARCHAR(20) NOT NULL,
                      daily_limit DECIMAL(19,4),
                      transaction_limit DECIMAL(19,4),
                      limit_currency VARCHAR(3),
                      issued_at TIMESTAMP NOT NULL
);