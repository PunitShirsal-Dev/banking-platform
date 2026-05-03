package com.bank.loanservice.domain.service;

import com.bank.loanservice.domain.model.*;
import com.bank.loanservice.domain.port.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoanDomainService {

    private final LoanRepository loanRepository;

    public RepaymentSchedule calculateSchedule(Money principal, int termMonths, BigDecimal annualRate) {
        BigDecimal monthlyRate = annualRate.divide(BigDecimal.valueOf(12.0 * 100.0), 10, RoundingMode.HALF_UP);
        BigDecimal principalAmount = principal.getAmount();
        BigDecimal monthlyPayment = principalAmount.multiply(monthlyRate)
                .divide(BigDecimal.ONE.subtract(BigDecimal.ONE.divide(
                                BigDecimal.ONE.add(monthlyRate).pow(termMonths), 10, RoundingMode.HALF_UP)),
                        2, RoundingMode.HALF_UP);

        List<Repayment> installments = new ArrayList<>();
        LocalDate dueDate = LocalDate.now().plusMonths(1);
        for (int i = 0; i < termMonths; i++) {
            installments.add(new Repayment(dueDate.plusMonths(i), monthlyPayment, false));
        }
        return new RepaymentSchedule(installments);
    }

    public Loan applyForLoan(LoanId id, LoanApplication application) {
        RepaymentSchedule schedule = calculateSchedule(application.getPrincipal(),
                application.getTermMonths(), application.getAnnualInterestRate());
        Loan loan = Loan.apply(id, application, schedule);
        loanRepository.save(loan);
        return loan;
    }

    public Loan approveLoan(LoanId id) {
        Loan loan = findById(id);
        loan.approve();
        loanRepository.save(loan);
        return loan;
    }

    public Loan disburseLoan(LoanId id) {
        Loan loan = findById(id);
        loan.disburse();
        loanRepository.save(loan);
        return loan;
    }

    public Loan findById(LoanId id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));
    }
}