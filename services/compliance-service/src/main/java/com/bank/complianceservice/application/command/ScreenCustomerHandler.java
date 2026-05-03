package com.bank.complianceservice.application.command;

import com.bank.complianceservice.domain.model.*;
import com.bank.complianceservice.domain.service.ScreeningDomainService;
import com.bank.complianceservice.domain.port.ComplianceEventPublisher;
import com.bank.complianceservice.shared.JsonLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScreenCustomerHandler {

    private static final JsonLogger log = JsonLogger.of(ScreenCustomerHandler.class);
    private final ScreeningDomainService domainService;
    private final ComplianceEventPublisher eventPublisher;

    @Transactional
    public ScreeningCase handle(ScreenCustomerCommand command) {
        CustomerSnapshot snapshot = new CustomerSnapshot(
                command.getCustomerId(), command.getFirstName(),
                command.getLastName(), command.getTaxId(), command.getCountry()
        );
        ScreeningCase sc = domainService.screenCustomer(snapshot);
        eventPublisher.publishScreeningCompleted(sc);
        log.event("ScreeningCompleted", sc);
        return sc;
    }
}