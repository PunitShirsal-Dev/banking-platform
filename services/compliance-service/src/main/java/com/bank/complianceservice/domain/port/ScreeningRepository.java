package com.bank.complianceservice.domain.port;

import com.bank.complianceservice.domain.model.ScreeningCase;
import com.bank.complianceservice.domain.model.ScreeningId;
import java.util.Optional;

public interface ScreeningRepository {
    void save(ScreeningCase screeningCase);
    Optional<ScreeningCase> findById(ScreeningId id);
}