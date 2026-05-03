package com.bank.customerservice.domain.port;

import com.bank.customerservice.domain.model.Customer;
import com.bank.customerservice.domain.model.CustomerId;
import java.util.Optional;

public interface CustomerRepository {
    void save(Customer customer);
    Optional<Customer> findById(CustomerId id);
}