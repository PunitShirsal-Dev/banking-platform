package com.bank.loanservice.application.command;

import com.bank.loanservice.domain.model.Loan;
import com.bank.loanservice.domain.model.LoanId;
import com.bank.loanservice.domain.service.LoanDomainService;
import com.bank.loanservice.domain.port.AccountServiceClient;
import com.bank.loanservice.domain.port.LoanEventPublisher;
import com.bank.loanservice.shared.JsonLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DisburseLoanHandler {

    private static final JsonLogger log = JsonLogger.of(DisburseLoanHandler.class);
    private final LoanDomainService domainService;
    private final AccountServiceClient accountServiceClient;
    private final LoanEventPublisher eventPublisher;

    @Transactional
    public Loan handle(DisburseLoanCommand command) {
        LoanId id = LoanId.of(UUID.fromString(command.getLoanId()));
        Loan loan = domainService.disburseLoan(id);
        // Credit the loan amount to the customer's account
        accountServiceClient.creditAccount(
                loan.application().getAccountId(),
                loan.application().getPrincipal(),
                "Loan disbursement " + id);
        eventPublisher.publishLoanDisbursed(loan);
        log.event("LoanDisbursed", loan);
        return loan;
    }
}