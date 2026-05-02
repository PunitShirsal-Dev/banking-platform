package com.bank.accountservice.domain.port;

import com.bank.accountservice.domain.model.Account;

public interface AccountEventPublisher {
    void publishAccountOpened(Account account);
    void publishAccountDebited(Account account);
    void publishAccountCredited(Account account);
}