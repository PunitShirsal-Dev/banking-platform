package com.bank.reportingservice.infrastructure.persistence;

import com.bank.reportingservice.domain.model.Report;
import com.bank.reportingservice.domain.model.ReportId;
import com.bank.reportingservice.domain.port.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReportPersistenceAdapter implements ReportRepository {

    private final ReportJpaRepository jpaRepository;
    private final ReportEntityMapper mapper;

    @Override
    @Transactional
    public void save(Report report) {
        jpaRepository.save(mapper.toEntity(report));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Report> findById(ReportId id) {
        return jpaRepository.findById(id.getValue()).map(mapper::toDomain);
    }
}