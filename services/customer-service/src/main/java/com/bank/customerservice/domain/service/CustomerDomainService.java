package com.bank.customerservice.domain.service;

import com.bank.customerservice.domain.model.*;
import com.bank.customerservice.domain.port.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerDomainService {

    private final CustomerRepository customerRepository;

    public Customer registerCustomer(CustomerId id, PersonalDetails details, Address address) {
        Customer customer = Customer.register(id, details, address);
        customerRepository.save(customer);
        return customer;
    }

    public Customer findById(CustomerId id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    }

    public void activateCustomer(CustomerId id) {
        Customer customer = findById(id);
        customer.activate();
        customerRepository.save(customer);
    }

    public void blockCustomer(CustomerId id) {
        Customer customer = findById(id);
        customer.block();
        customerRepository.save(customer);
    }

    public void startKyc(CustomerId id) {
        Customer customer = findById(id);
        customer.startKyc();
        customerRepository.save(customer);
    }

    public void verifyKyc(CustomerId id) {
        Customer customer = findById(id);
        customer.verifyKyc();
        customerRepository.save(customer);
    }
}