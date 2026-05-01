-- V1__init_account_table.sql
CREATE TABLE account (
  id UUID PRIMARY KEY,
  balance DECIMAL(19,4) NOT NULL,
  currency VARCHAR(3) NOT NULL
);