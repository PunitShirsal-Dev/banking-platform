package com.bank.accountservice.application.command;

import com.bank.accountservice.domain.model.Account;
import com.bank.accountservice.domain.model.AccountId;
import com.bank.accountservice.domain.model.Money;
import com.bank.accountservice.domain.service.AccountDomainService;
import com.bank.accountservice.domain.port.AccountEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreditAccountHandler {

    private final AccountDomainService accountDomainService;
    private final AccountEventPublisher eventPublisher;

    @Transactional
    public Account handle(CreditAccountCommand command) {
        AccountId accountId = AccountId.of(UUID.fromString(command.accountId()));
        Money money = Money.of(command.amount(), command.currency());
        Account account = accountDomainService.credit(accountId, money, command.description());
        eventPublisher.publishAccountCredited(account);
        return account;
    }
}