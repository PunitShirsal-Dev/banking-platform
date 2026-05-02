package com.bank.accountservice.application.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountBalanceQuery {

    private final AccountBalanceProjection projection;

    public BigDecimal getBalance(String accountId) {
        return projection.getBalance(accountId);
    }
}