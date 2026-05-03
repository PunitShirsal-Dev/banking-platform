package com.bank.loanservice.domain.port;

import com.bank.loanservice.domain.model.Loan;
import com.bank.loanservice.domain.model.LoanId;
import java.util.Optional;

public interface LoanRepository {
    void save(Loan loan);
    Optional<Loan> findById(LoanId id);
}