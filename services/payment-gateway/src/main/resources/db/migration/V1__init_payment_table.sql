CREATE TABLE payment_order (
                               id UUID PRIMARY KEY,
                               source_account_id VARCHAR(36) NOT NULL,
                               beneficiary_name VARCHAR(255),
                               iban VARCHAR(34),
                               bic VARCHAR(11),
                               bank_name VARCHAR(255),
                               country_code VARCHAR(2),
                               amount DECIMAL(19,4) NOT NULL,
                               currency VARCHAR(3) NOT NULL,
                               payment_type VARCHAR(20) NOT NULL,
                               status VARCHAR(20) NOT NULL,
                               reference VARCHAR(255),
                               created_at TIMESTAMP NOT NULL
);