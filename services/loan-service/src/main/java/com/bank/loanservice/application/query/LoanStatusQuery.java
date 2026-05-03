package com.bank.loanservice.application.query;

import com.bank.loanservice.application.dto.LoanResponseDTO;
import com.bank.loanservice.domain.model.*;
import com.bank.loanservice.domain.port.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoanStatusQuery {

    private final LoanRepository repository;

    public LoanResponseDTO getLoan(String loanId) {
        Loan loan = repository.findById(LoanId.of(UUID.fromString(loanId)))
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));
        return toDto(loan);
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