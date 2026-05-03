package com.bank.cardservice.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CardJpaRepository extends JpaRepository<BankCardEntity, UUID> {}