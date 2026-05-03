package com.bank.complianceservice.infrastructure.persistence;

import com.bank.complianceservice.domain.model.ScreeningCase;
import com.bank.complianceservice.domain.model.ScreeningId;
import com.bank.complianceservice.domain.port.ScreeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ScreeningPersistenceAdapter implements ScreeningRepository {

    private final ScreeningJpaRepository jpaRepository;
    private final ScreeningEntityMapper mapper;

    @Override
    @Transactional
    public void save(ScreeningCase sc) {
        jpaRepository.save(mapper.toEntity(sc));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ScreeningCase> findById(ScreeningId id) {
        return jpaRepository.findById(id.getValue()).map(mapper::toDomain);
    }
}