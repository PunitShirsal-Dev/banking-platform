package com.bank.customerservice.infrastructure.web;

import com.bank.customerservice.application.command.*;
import com.bank.customerservice.application.dto.*;
import com.bank.customerservice.application.query.CustomerProfileQuery;
import com.bank.customerservice.domain.model.Customer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final RegisterCustomerHandler registerHandler;
    private final CustomerProfileQuery profileQuery;

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> register(
            @RequestHeader("X-Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody RegisterCustomerRequestDTO request) {

        RegisterCustomerCommand command = new RegisterCustomerCommand(
                request.getFirstName(), request.getLastName(),
                request.getEmail(), request.getPhoneNumber(), request.getTaxId(),
                request.getStreet(), request.getCity(), request.getState(),
                request.getZipCode(), request.getCountry(),
                idempotencyKey
        );
        Customer customer = registerHandler.handle(command);
        return ResponseEntity.ok(new CustomerResponseDTO(
                customer.id().toString(),
                customer.personalDetails().getFirstName(),
                customer.personalDetails().getLastName(),
                customer.personalDetails().getEmail(),
                customer.status().name(),
                customer.kycStatus().name()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getProfile(@PathVariable String id) {
        CustomerResponseDTO dto = profileQuery.getProfile(id);
        return ResponseEntity.ok(dto);
    }
}