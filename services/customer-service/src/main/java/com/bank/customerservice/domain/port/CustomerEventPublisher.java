package com.bank.customerservice.domain.port;

import com.bank.customerservice.domain.model.Customer;

public interface CustomerEventPublisher {
    void publishCustomerRegistered(Customer customer);
    void publishCustomerActivated(Customer customer);
    void publishCustomerBlocked(Customer customer);
    void publishKycVerified(Customer customer);
}