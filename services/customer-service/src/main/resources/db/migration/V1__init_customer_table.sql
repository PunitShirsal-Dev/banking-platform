CREATE TABLE customer (
                          id UUID PRIMARY KEY,
                          first_name VARCHAR(100) NOT NULL,
                          last_name VARCHAR(100) NOT NULL,
                          email VARCHAR(255) NOT NULL,
                          phone_number VARCHAR(30),
                          tax_id VARCHAR(50),
                          street VARCHAR(255),
                          city VARCHAR(100),
                          state VARCHAR(100),
                          zip_code VARCHAR(20),
                          country VARCHAR(100),
                          status VARCHAR(20) NOT NULL,
                          kyc_status VARCHAR(20) NOT NULL,
                          registered_at TIMESTAMP NOT NULL
);