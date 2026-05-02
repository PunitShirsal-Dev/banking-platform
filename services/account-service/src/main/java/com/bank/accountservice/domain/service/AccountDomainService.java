package com.bank.accountservice.domain.service;

import com.bank.accountservice.domain.model.*;
import com.bank.accountservice.domain.port.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountDomainService {

    private final AccountRepository accountRepository;

    public Account openAccount(AccountId id, String customerId, AccountType type, Money initialDeposit) {
        Account account = Account.open(id, customerId, type, initialDeposit);
        accountRepository.save(account);
        return account;
    }

    public Account findById(AccountId id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }

    public Account debit(AccountId accountId, Money amount, String description) {
        Account account = findById(accountId);
        account.debit(amount, description);
        accountRepository.save(account);
        return account;
    }

    public Account credit(AccountId accountId, Money amount, String description) {
        Account account = findById(accountId);
        account.credit(amount, description);
        accountRepository.save(account);
        return account;
    }
}