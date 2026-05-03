package com.bank.loanservice.application.command;

import com.bank.loanservice.domain.model.Loan;
import com.bank.loanservice.domain.model.LoanId;
import com.bank.loanservice.domain.service.LoanDomainService;
import com.bank.loanservice.domain.port.LoanEventPublisher;
import com.bank.loanservice.shared.JsonLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApproveLoanHandler {

    private static final JsonLogger log = JsonLogger.of(ApproveLoanHandler.class);
    private final LoanDomainService domainService;
    private final LoanEventPublisher eventPublisher;

    @Transactional
    public Loan handle(ApproveLoanCommand command) {
        LoanId id = LoanId.of(UUID.fromString(command.getLoanId()));
        Loan loan = domainService.approveLoan(id);
        eventPublisher.publishLoanApproved(loan);
        log.event("LoanApproved", loan);
        return loan;
    }
}