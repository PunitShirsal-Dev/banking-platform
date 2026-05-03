package com.bank.loanservice.infrastructure.web;

import com.bank.loanservice.application.command.*;
import com.bank.loanservice.application.dto.*;
import com.bank.loanservice.application.query.LoanStatusQuery;
import com.bank.loanservice.domain.model.Loan;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final ApplyForLoanHandler applyHandler;
    private final ApproveLoanHandler approveHandler;
    private final DisburseLoanHandler disburseHandler;
    private final LoanStatusQuery statusQuery;

    @PostMapping
    public ResponseEntity<LoanResponseDTO> apply(
            @RequestHeader("X-Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody LoanApplicationRequestDTO request) {
        Loan loan = applyHandler.handle(new ApplyForLoanCommand(
                request.getCustomerId(), request.getAccountId(), request.getLoanType(),
                request.getPrincipalAmount(), request.getCurrency(),
                request.getTermMonths(), request.getAnnualInterestRate(), idempotencyKey));
        return ResponseEntity.ok(toDto(loan));
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<LoanResponseDTO> approve(
            @RequestHeader("X-Idempotency-Key") String idempotencyKey,
            @PathVariable String id) {
        Loan loan = approveHandler.handle(new ApproveLoanCommand(id, idempotencyKey));
        return ResponseEntity.ok(toDto(loan));
    }

    @PostMapping("/{id}/disburse")
    public ResponseEntity<LoanResponseDTO> disburse(
            @RequestHeader("X-Idempotency-Key") String idempotencyKey,
            @PathVariable String id) {
        Loan loan = disburseHandler.handle(new DisburseLoanCommand(id, idempotencyKey));
        return ResponseEntity.ok(toDto(loan));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanResponseDTO> getLoan(@PathVariable String id) {
        return ResponseEntity.ok(statusQuery.getLoan(id));
    }

    private LoanResponseDTO toDto(Loan loan) {
        return new LoanResponseDTO(
                loan.id().toString(),
                loan.application().getCustomerId(),
                loan.application().getType().name(),
                loan.application().getPrincipal().getAmount().toString(),
                loan.application().getPrincipal().getCurrency().getCurrencyCode(),
                loan.status().name(),
                loan.application().getTermMonths()
        );
    }
}