package com.bank.reportingservice.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ReportJpaRepository extends JpaRepository<ReportEntity, UUID> {}