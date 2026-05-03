package com.bank.loanservice.infrastructure.messaging;

import com.bank.loanservice.domain.model.Loan;
import com.bank.loanservice.domain.port.LoanEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaLoanEventPublisher implements LoanEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publishLoanApplied(Loan loan) {
        kafkaTemplate.send("loan.applied", loan.id().toString(), loan);
    }

    @Override
    public void publishLoanApproved(Loan loan) {
        kafkaTemplate.send("loan.approved", loan.id().toString(), loan);
    }

    @Override
    public void publishLoanDisbursed(Loan loan) {
        kafkaTemplate.send("loan.disbursed", loan.id().toString(), loan);
    }
}