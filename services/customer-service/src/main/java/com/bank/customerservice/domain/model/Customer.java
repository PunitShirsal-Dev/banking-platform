package com.bank.customerservice.domain.model;

import lombok.Getter;
import lombok.experimental.Accessors;
import java.time.Instant;

@Getter
@Accessors(fluent = true)
public class Customer {
    private CustomerId id;
    private PersonalDetails personalDetails;
    private Address primaryAddress;
    private CustomerStatus status;
    private KycStatus kycStatus;
    private Instant registeredAt;

    private Customer() {}

    public static Customer register(CustomerId id, PersonalDetails details, Address address) {
        Customer customer = new Customer();
        customer.id = id;
        customer.personalDetails = details;
        customer.primaryAddress = address;
        customer.status = CustomerStatus.PENDING;
        customer.kycStatus = KycStatus.NOT_STARTED;
        customer.registeredAt = Instant.now();
        return customer;
    }

    public void activate() {
        if (status != CustomerStatus.PENDING) throw new IllegalStateException("Only pending customers can be activated");
        status = CustomerStatus.ACTIVE;
    }

    public void block() {
        status = CustomerStatus.BLOCKED;
    }

    public void close() {
        status = CustomerStatus.CLOSED;
    }

    public void startKyc() {
        if (kycStatus != KycStatus.NOT_STARTED) throw new IllegalStateException("KYC already started");
        kycStatus = KycStatus.IN_PROGRESS;
    }

    public void verifyKyc() {
        if (kycStatus != KycStatus.IN_PROGRESS) throw new IllegalStateException("KYC not in progress");
        kycStatus = KycStatus.VERIFIED;
    }

    // Reconstitution from persistence (package‑private access)
    public static Customer reconstitute(CustomerId id, PersonalDetails details, Address address,
                                        CustomerStatus status, KycStatus kycStatus, Instant registeredAt) {
        Customer customer = new Customer();
        customer.id = id;
        customer.personalDetails = details;
        customer.primaryAddress = address;
        customer.status = status;
        customer.kycStatus = kycStatus;
        customer.registeredAt = registeredAt;
        return customer;
    }
}