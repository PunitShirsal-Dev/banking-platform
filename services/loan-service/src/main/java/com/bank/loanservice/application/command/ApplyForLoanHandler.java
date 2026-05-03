package com.bank.loanservice.application.command;

import com.bank.loanservice.domain.model.*;
import com.bank.loanservice.domain.service.LoanDomainService;
import com.bank.loanservice.domain.port.LoanEventPublisher;
import com.bank.loanservice.shared.JsonLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplyForLoanHandler {

    private static final JsonLogger log = JsonLogger.of(ApplyForLoanHandler.class);
    private final LoanDomainService domainService;
    private final LoanEventPublisher eventPublisher;

    @Transactional
    public Loan handle(ApplyForLoanCommand command) {
        LoanId id = LoanId.random();
        Money principal = Money.of(command.getPrincipalAmount(), command.getCurrency());
        LoanType type = LoanType.valueOf(command.getLoanType().toUpperCase());
        LoanApplication application = new LoanApplication(
                command.getCustomerId(), command.getAccountId(),
                type, principal, command.getTermMonths(),
                command.getAnnualInterestRate()
        );
        Loan loan = domainService.applyForLoan(id, application);
        eventPublisher.publishLoanApplied(loan);
        log.event("LoanApplied", loan);
        return loan;
    }
}