package com.bank.accountservice.infrastructure.persistence;

import com.bank.accountservice.domain.model.*;
import org.springframework.stereotype.Component;

@Component
public class AccountEntityMapper {

    public AccountEntity toEntity(Account account) {
        AccountEntity entity = new AccountEntity();
        entity.setId(account.id().getValue());
        entity.setCustomerId(account.customerId());
        entity.setType(AccountTypeEntity.valueOf(account.type().name()));
        entity.setStatus(AccountStatusEntity.valueOf(account.status().name()));
        entity.setBalanceAmount(account.balance().getAmount());
        entity.setBalanceCurrency(account.balance().getCurrency().getCurrencyCode());
        entity.setOpenedAt(account.openedAt());
        entity.setClosedAt(account.closedAt());

        entity.getTransactionLogs().clear();
        account.transactionLogs().forEach(log -> {
            TransactionLogEntity logEntity = toLogEntity(log);
            entity.addTransactionLog(logEntity);
        });
        return entity;
    }

    public Account toDomain(AccountEntity entity) {
        AccountId id = AccountId.of(entity.getId());
        Money balance = Money.of(entity.getBalanceAmount(), entity.getBalanceCurrency());
        AccountType type = AccountType.valueOf(entity.getType().name());
        AccountStatus status = AccountStatus.valueOf(entity.getStatus().name());

        var logs = entity.getTransactionLogs().stream()
                .map(this::toDomainLog)
                .toList();

        Account.GenericAccountArg accountArg = new Account.GenericAccountArg(
                entity.getCustomerId(), entity.getOpenedAt(), entity.getClosedAt());

        return Account.reconstitute(
                id,
                type,
                balance,
                status,
                logs,
                accountArg
        );
    }

    private TransactionLogEntity toLogEntity(TransactionLog log) {
        TransactionLogEntity entity = new TransactionLogEntity();
        entity.setId(java.util.UUID.randomUUID()); // or derive from domain if stored
        entity.setType(TransactionTypeEntity.valueOf(log.getType().name()));
        entity.setAmount(log.getAmount().getAmount());
        entity.setCurrency(log.getAmount().getCurrency().getCurrencyCode());
        entity.setDescription(log.getDescription());
        entity.setTimestamp(log.getTimestamp());
        return entity;
    }

    private TransactionLog toDomainLog(TransactionLogEntity logEntity) {
        Money amount = Money.of(logEntity.getAmount(), logEntity.getCurrency());
        TransactionType type = TransactionType.valueOf(logEntity.getType().name());
        return new TransactionLog(type, amount, logEntity.getDescription(), logEntity.getTimestamp());
    }
}