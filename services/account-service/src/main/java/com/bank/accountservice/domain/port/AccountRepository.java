package com.bank.accountservice.domain.port;

import com.bank.accountservice.domain.model.Account;
import com.bank.accountservice.domain.model.AccountId;
import java.util.Optional;

public interface AccountRepository {
    void save(Account account);
    Optional<Account> findById(AccountId id);
}