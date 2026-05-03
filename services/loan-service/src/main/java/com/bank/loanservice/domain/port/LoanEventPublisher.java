package com.bank.loanservice.domain.port;

import com.bank.loanservice.domain.model.Loan;

public interface LoanEventPublisher {
    void publishLoanApplied(Loan loan);
    void publishLoanApproved(Loan loan);
    void publishLoanDisbursed(Loan loan);
}