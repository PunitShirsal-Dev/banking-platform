package com.bank.accountservice.infrastructure.persistence;

import com.bank.accountservice.domain.model.Account;
import com.bank.accountservice.domain.model.AccountId;
import com.bank.accountservice.domain.port.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccountPersistenceAdapter implements AccountRepository {

    private final AccountJpaRepository jpaRepository;
    private final AccountEntityMapper mapper;

    @Override
    @Transactional
    public void save(Account account) {
        AccountEntity entity = mapper.toEntity(account);
        jpaRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Account> findById(AccountId id) {
        return jpaRepository.findById(id.getValue()).map(mapper::toDomain);
    }
}