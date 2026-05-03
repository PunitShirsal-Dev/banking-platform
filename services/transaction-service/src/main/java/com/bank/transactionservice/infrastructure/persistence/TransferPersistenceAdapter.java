package com.bank.transactionservice.infrastructure.persistence;

import com.bank.transactionservice.domain.model.TransactionId;
import com.bank.transactionservice.domain.model.TransferAggregate;
import com.bank.transactionservice.domain.port.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TransferPersistenceAdapter implements TransferRepository {

    private final TransferJpaRepository jpaRepository;
    private final TransferEntityMapper mapper;

    @Override
    @Transactional
    public void save(TransferAggregate transfer) {
        TransferEntity entity = mapper.toEntity(transfer);
        jpaRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TransferAggregate> findById(TransactionId id) {
        return jpaRepository.findById(id.getValue()).map(mapper::toDomain);
    }
}