package com.bank.loanservice.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LoanJpaRepository extends JpaRepository<LoanEntity, UUID> {}