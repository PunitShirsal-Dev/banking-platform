package com.bank.complianceservice.domain.port;

import com.bank.complianceservice.domain.model.ScreeningCase;

public interface ComplianceEventPublisher {
    void publishScreeningCompleted(ScreeningCase sc);
    void publishScreeningEscalated(ScreeningCase sc);
}