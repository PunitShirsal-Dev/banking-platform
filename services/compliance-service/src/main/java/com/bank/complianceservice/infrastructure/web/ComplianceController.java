package com.bank.complianceservice.infrastructure.web;

import com.bank.complianceservice.application.command.*;
import com.bank.complianceservice.application.dto.*;
import com.bank.complianceservice.application.query.ScreeningQuery;
import com.bank.complianceservice.domain.model.ScreeningCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compliance")
@RequiredArgsConstructor
public class ComplianceController {

    private final ScreenCustomerHandler screenHandler;
    private final ScreeningQuery screeningQuery;

    @PostMapping("/screen")
    public ResponseEntity<ScreeningResponseDTO> screen(
            @RequestHeader("X-Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody ScreeningRequestDTO request) {

        ScreenCustomerCommand cmd = new ScreenCustomerCommand(
                request.getCustomerId(), request.getFirstName(),
                request.getLastName(), request.getTaxId(),
                request.getCountry(), idempotencyKey
        );
        ScreeningCase sc = screenHandler.handle(cmd);
        return ResponseEntity.ok(new ScreeningResponseDTO(
                sc.id().getValue().toString(),
                sc.customer().getFirstName() + " " + sc.customer().getLastName(),
                sc.result().name(),
                sc.caseStatus().name(),
                sc.matches().stream().map(m -> m.getListName() + ":" + m.getMatchedField()).toList()
        ));
    }

    @GetMapping("/screenings/{id}")
    public ResponseEntity<ScreeningResponseDTO> getScreening(@PathVariable String id) {
        return ResponseEntity.ok(screeningQuery.getScreeningResult(id));
    }
}