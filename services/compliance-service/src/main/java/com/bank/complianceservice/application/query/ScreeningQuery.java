package com.bank.complianceservice.application.query;

import com.bank.complianceservice.application.dto.ScreeningResponseDTO;
import com.bank.complianceservice.domain.model.ScreeningCase;
import com.bank.complianceservice.domain.model.ScreeningId;
import com.bank.complianceservice.domain.port.ScreeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScreeningQuery {

    private final ScreeningRepository repository;

    public ScreeningResponseDTO getScreeningResult(String screeningId) {
        ScreeningCase sc = repository.findById(ScreeningId.of(UUID.fromString(screeningId)))
                .orElseThrow(() -> new IllegalArgumentException("Screening not found"));
        return toDto(sc);
    }

    private ScreeningResponseDTO toDto(ScreeningCase sc) {
        List<String> matchDescs = sc.matches().stream()
                .map(m -> m.getListName() + ":" + m.getMatchedField() + "=" + m.getMatchedValue())
                .toList();
        return new ScreeningResponseDTO(
                sc.id().getValue().toString(),
                sc.customer().getFirstName() + " " + sc.customer().getLastName(),
                sc.result().name(),
                sc.caseStatus().name(),
                matchDescs
        );
    }
}