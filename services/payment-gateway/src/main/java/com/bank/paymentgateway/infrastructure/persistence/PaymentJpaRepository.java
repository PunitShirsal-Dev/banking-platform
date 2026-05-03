package com.bank.paymentgateway.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PaymentJpaRepository extends JpaRepository<PaymentOrderEntity, UUID> {}