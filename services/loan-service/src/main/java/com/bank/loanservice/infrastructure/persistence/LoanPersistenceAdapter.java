package com.bank.loanservice.infrastructure.persistence;

import com.bank.loanservice.domain.model.Loan;
import com.bank.loanservice.domain.model.LoanId;
import com.bank.loanservice.domain.port.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LoanPersistenceAdapter implements LoanRepository {

    private final LoanJpaRepository jpaRepository;
    private final LoanEntityMapper mapper;

    @Override
    @Transactional
    public void save(Loan loan) {
        jpaRepository.save(mapper.toEntity(loan));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Loan> findById(LoanId id) {
        return jpaRepository.findById(id.getValue()).map(mapper::toDomain);
    }
}