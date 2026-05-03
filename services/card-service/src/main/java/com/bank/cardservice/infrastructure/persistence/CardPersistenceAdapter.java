package com.bank.cardservice.infrastructure.persistence;

import com.bank.cardservice.domain.model.BankCard;
import com.bank.cardservice.domain.model.CardId;
import com.bank.cardservice.domain.port.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CardPersistenceAdapter implements CardRepository {

    private final CardJpaRepository jpaRepository;
    private final CardEntityMapper mapper;

    @Override
    @Transactional
    public void save(BankCard bankCard) {
        jpaRepository.save(mapper.toEntity(bankCard));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BankCard> findById(CardId id) {
        return jpaRepository.findById(id.getValue()).map(mapper::toDomain);
    }
}