package com.bank.loanservice.domain.port;

import com.bank.loanservice.domain.model.Money;

public interface AccountServiceClient {
    void creditAccount(String accountId, Money amount, String description);
}