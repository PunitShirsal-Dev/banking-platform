package com.bank.customerservice.application.query;

import com.bank.customerservice.application.dto.CustomerResponseDTO;
import com.bank.customerservice.domain.model.*;
import com.bank.customerservice.domain.port.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerProfileQuery {

    private final CustomerRepository repository;

    public CustomerResponseDTO getProfile(String customerId) {
        Customer customer = repository.findById(CustomerId.of(UUID.fromString(customerId)))
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        return toDto(customer);
    }

    private CustomerResponseDTO toDto(Customer c) {
        return new CustomerResponseDTO(
                c.id().toString(),
                c.personalDetails().getFirstName(),
                c.personalDetails().getLastName(),
                c.personalDetails().getEmail(),
                c.status().name(),
                c.kycStatus().name()
        );
    }
}