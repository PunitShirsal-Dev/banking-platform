package com.bank.reportingservice.infrastructure.web;

import com.bank.reportingservice.application.command.*;
import com.bank.reportingservice.application.dto.*;
import com.bank.reportingservice.application.query.ReportQueryService;
import com.bank.reportingservice.domain.model.Report;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final GenerateReportHandler generateHandler;
    private final ReportQueryService queryService;

    @PostMapping
    public ResponseEntity<ReportResponseDTO> generate(
            @RequestHeader("X-Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody ReportRequestDTO request) {
        GenerateReportCommand cmd = new GenerateReportCommand(
                request.getCustomerId(), request.getType(),
                request.getStartDate(), request.getEndDate(), idempotencyKey);
        Report report = generateHandler.handle(cmd);
        return ResponseEntity.accepted().body(new ReportResponseDTO(
                report.id().toString(), report.definition().getType().name(), report.status().name(), null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportResponseDTO> getReport(@PathVariable String id) {
        return ResponseEntity.ok(queryService.getReport(id));
    }
}