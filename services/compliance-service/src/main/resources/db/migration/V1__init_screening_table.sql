CREATE TABLE screening_case (
                                id UUID PRIMARY KEY,
                                customer_id VARCHAR(36) NOT NULL,
                                first_name VARCHAR(100),
                                last_name VARCHAR(100),
                                tax_id VARCHAR(50),
                                country VARCHAR(100),
                                result VARCHAR(20) NOT NULL,
                                matches_json TEXT,
                                case_status VARCHAR(20) NOT NULL,
                                screened_at TIMESTAMP NOT NULL
);