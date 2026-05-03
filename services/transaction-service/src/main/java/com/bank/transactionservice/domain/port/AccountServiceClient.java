package com.bank.transactionservice.domain.port;

import com.bank.transactionservice.domain.model.AccountId;
import com.bank.transactionservice.domain.model.Money;

public interface AccountServiceClient {
    void debit(AccountId accountId, Money amount, String description);
    void credit(AccountId accountId, Money amount, String description);
}