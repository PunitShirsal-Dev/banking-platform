package com.bank.paymentgateway.infrastructure.persistence;

import com.bank.paymentgateway.domain.model.PaymentOrder;
import com.bank.paymentgateway.domain.model.PaymentId;
import com.bank.paymentgateway.domain.port.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PaymentPersistenceAdapter implements PaymentRepository {

    private final PaymentJpaRepository jpaRepository;
    private final PaymentEntityMapper mapper;

    @Override
    @Transactional
    public void save(PaymentOrder order) {
        jpaRepository.save(mapper.toEntity(order));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentOrder> findById(PaymentId id) {
        return jpaRepository.findById(id.getValue()).map(mapper::toDomain);
    }
}