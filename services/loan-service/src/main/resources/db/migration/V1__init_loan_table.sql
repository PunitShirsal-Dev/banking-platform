CREATE TABLE loan (
                      id UUID PRIMARY KEY,
                      customer_id VARCHAR(36) NOT NULL,
                      account_id VARCHAR(36) NOT NULL,
                      type VARCHAR(20) NOT NULL,
                      principal_amount DECIMAL(19,4) NOT NULL,
                      currency VARCHAR(3) NOT NULL,
                      term_months INT NOT NULL,
                      annual_interest_rate DECIMAL(5,2) NOT NULL,
                      status VARCHAR(20) NOT NULL,
                      schedule_json TEXT,
                      applied_at TIMESTAMP NOT NULL
);