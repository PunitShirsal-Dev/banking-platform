package com.bank.loanservice.domain.model;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@Accessors(fluent = true)
public class Loan {
    private LoanId id;
    private LoanApplication application;
    private LoanStatus status;
    private RepaymentSchedule schedule;
    private Instant appliedAt;

    private Loan() {}

    public static Loan apply(LoanId id, LoanApplication application, RepaymentSchedule schedule) {
        Loan loan = new Loan();
        loan.id = id;
        loan.application = application;
        loan.schedule = schedule;
        loan.status = LoanStatus.PENDING;
        loan.appliedAt = Instant.now();
        return loan;
    }

    public void approve() {
        if (status != LoanStatus.PENDING) throw new IllegalStateException("Only pending loans can be approved");
        status = LoanStatus.APPROVED;
    }

    public void disburse() {
        if (status != LoanStatus.APPROVED) throw new IllegalStateException("Loan must be approved first");
        status = LoanStatus.DISBURSED;
    }

    public void markAsRepaid() {
        status = LoanStatus.REPAID;
    }

    public void markAsDefaulted() {
        status = LoanStatus.DEFAULTED;
    }

    // Reconstitution
    public static Loan reconstitute(LoanId id, LoanApplication application, LoanStatus status,
                                    RepaymentSchedule schedule, Instant appliedAt) {
        Loan loan = new Loan();
        loan.id = id;
        loan.application = application;
        loan.status = status;
        loan.schedule = schedule;
        loan.appliedAt = appliedAt;
        return loan;
    }
}