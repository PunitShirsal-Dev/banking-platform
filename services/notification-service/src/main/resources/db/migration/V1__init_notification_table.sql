CREATE TABLE notification (
                              id UUID PRIMARY KEY,
                              type VARCHAR(10) NOT NULL,
                              email VARCHAR(255),
                              phone_number VARCHAR(30),
                              device_token VARCHAR(255),
                              subject VARCHAR(500),
                              body TEXT,
                              status VARCHAR(20) NOT NULL,
                              created_at TIMESTAMP NOT NULL
);