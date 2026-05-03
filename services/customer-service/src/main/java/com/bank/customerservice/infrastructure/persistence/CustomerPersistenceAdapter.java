package com.bank.customerservice.infrastructure.persistence;

import com.bank.customerservice.domain.model.Customer;
import com.bank.customerservice.domain.model.CustomerId;
import com.bank.customerservice.domain.port.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomerPersistenceAdapter implements CustomerRepository {

    private final CustomerJpaRepository jpaRepository;
    private final CustomerEntityMapper mapper;

    @Override
    @Transactional
    public void save(Customer customer) {
        CustomerEntity entity = mapper.toEntity(customer);
        jpaRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> findById(CustomerId id) {
        return jpaRepository.findById(id.getValue()).map(mapper::toDomain);
    }
}