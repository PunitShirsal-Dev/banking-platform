package com.bank.accountservice.application.query;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class AccountBalanceProjection {
    private final JdbcTemplate jdbc;

    public AccountBalanceProjection(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public BigDecimal getBalance(String accountId) {
        return jdbc.queryForObject(
                "SELECT balance_amount FROM account WHERE id = ?::uuid",
                BigDecimal.class, accountId);
    }
}