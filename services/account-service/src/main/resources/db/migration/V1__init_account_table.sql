CREATE TABLE account (
                         id UUID PRIMARY KEY,
                         customer_id VARCHAR(36) NOT NULL,
                         type VARCHAR(20) NOT NULL,
                         status VARCHAR(20) NOT NULL,
                         balance_amount DECIMAL(19,4) NOT NULL,
                         balance_currency VARCHAR(3) NOT NULL,
                         opened_at TIMESTAMP NOT NULL,
                         closed_at TIMESTAMP
);

CREATE TABLE transaction_log (
                                 id UUID PRIMARY KEY,
                                 account_id UUID NOT NULL REFERENCES account(id),
                                 type VARCHAR(10) NOT NULL,
                                 amount DECIMAL(19,4) NOT NULL,
                                 currency VARCHAR(3) NOT NULL,
                                 description TEXT,
                                 timestamp TIMESTAMP NOT NULL
);