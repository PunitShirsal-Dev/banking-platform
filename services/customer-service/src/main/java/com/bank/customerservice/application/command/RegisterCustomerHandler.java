package com.bank.customerservice.application.command;

import com.bank.customerservice.domain.model.*;
import com.bank.customerservice.domain.service.CustomerDomainService;
import com.bank.customerservice.domain.port.CustomerEventPublisher;
import com.bank.customerservice.shared.JsonLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterCustomerHandler {

    private static final JsonLogger log = JsonLogger.of(RegisterCustomerHandler.class);

    private final CustomerDomainService domainService;
    private final CustomerEventPublisher eventPublisher;

    @Transactional
    public Customer handle(RegisterCustomerCommand command) {
        CustomerId id = CustomerId.random();
        PersonalDetails details = new PersonalDetails(
                command.getFirstName(), command.getLastName(),
                command.getEmail(), command.getPhoneNumber(),
                command.getTaxId()
        );
        Address address = new Address(
                command.getStreet(), command.getCity(),
                command.getState(), command.getZipCode(),
                command.getCountry()
        );

        Customer customer = domainService.registerCustomer(id, details, address);
        eventPublisher.publishCustomerRegistered(customer);
        log.event("CustomerRegistered", customer);
        return customer;
    }
}