package com.bank.customerservice.infrastructure.persistence;

import com.bank.customerservice.domain.model.*;
import org.springframework.stereotype.Component;

@Component
public class CustomerEntityMapper {

    public CustomerEntity toEntity(Customer customer) {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(customer.id().getValue());
        entity.setFirstName(customer.personalDetails().getFirstName());
        entity.setLastName(customer.personalDetails().getLastName());
        entity.setEmail(customer.personalDetails().getEmail());
        entity.setPhoneNumber(customer.personalDetails().getPhoneNumber());
        entity.setTaxId(customer.personalDetails().getTaxId());
        entity.setStreet(customer.primaryAddress().getStreet());
        entity.setCity(customer.primaryAddress().getCity());
        entity.setState(customer.primaryAddress().getState());
        entity.setZipCode(customer.primaryAddress().getZipCode());
        entity.setCountry(customer.primaryAddress().getCountry());
        entity.setStatus(CustomerStatusEntity.valueOf(customer.status().name()));
        entity.setKycStatus(KycStatusEntity.valueOf(customer.kycStatus().name()));
        entity.setRegisteredAt(customer.registeredAt());
        return entity;
    }

    public Customer toDomain(CustomerEntity entity) {
        CustomerId id = CustomerId.of(entity.getId());
        PersonalDetails details = new PersonalDetails(
                entity.getFirstName(), entity.getLastName(),
                entity.getEmail(), entity.getPhoneNumber(), entity.getTaxId()
        );
        Address address = new Address(
                entity.getStreet(), entity.getCity(),
                entity.getState(), entity.getZipCode(), entity.getCountry()
        );
        CustomerStatus status = CustomerStatus.valueOf(entity.getStatus().name());
        KycStatus kycStatus = KycStatus.valueOf(entity.getKycStatus().name());
        return Customer.reconstitute(id, details, address, status, kycStatus, entity.getRegisteredAt());
    }
}