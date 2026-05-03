package com.bank.complianceservice.infrastructure.persistence;

import com.bank.complianceservice.domain.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class ScreeningEntityMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public ScreeningCaseEntity toEntity(ScreeningCase sc) {
        ScreeningCaseEntity entity = new ScreeningCaseEntity();
        entity.setId(sc.id().getValue());
        entity.setCustomerId(sc.customer().getCustomerId());
        entity.setFirstName(sc.customer().getFirstName());
        entity.setLastName(sc.customer().getLastName());
        entity.setTaxId(sc.customer().getTaxId());
        entity.setCountry(sc.customer().getCountry());
        entity.setResult(ScreeningResultEntity.valueOf(sc.result().name()));
        entity.setCaseStatus(CaseStatusEntity.valueOf(sc.caseStatus().name()));
        entity.setScreenedAt(sc.screenedAt());
        try {
            entity.setMatchesJson(objectMapper.writeValueAsString(sc.matches()));
        } catch (JsonProcessingException e) {
            entity.setMatchesJson("[]");
        }
        return entity;
    }

    public ScreeningCase toDomain(ScreeningCaseEntity entity) {
        ScreeningId id = ScreeningId.of(entity.getId());
        CustomerSnapshot snapshot = new CustomerSnapshot(
                entity.getCustomerId(), entity.getFirstName(),
                entity.getLastName(), entity.getTaxId(), entity.getCountry()
        );
        ScreeningResult result = ScreeningResult.valueOf(entity.getResult().name());
        CaseStatus caseStatus = CaseStatus.valueOf(entity.getCaseStatus().name());

        List<WatchlistMatch> matches;
        try {
            WatchlistMatch[] arr = objectMapper.readValue(entity.getMatchesJson(), WatchlistMatch[].class);
            matches = Arrays.asList(arr);
        } catch (Exception e) {
            matches = Collections.emptyList();
        }

        return ScreeningCase.reconstitute(id, snapshot, result, matches, caseStatus, entity.getScreenedAt());
    }
}