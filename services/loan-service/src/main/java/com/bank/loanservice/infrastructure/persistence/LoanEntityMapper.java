package com.bank.loanservice.infrastructure.persistence;

import com.bank.loanservice.domain.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class LoanEntityMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public LoanEntity toEntity(Loan loan) {
        LoanEntity entity = new LoanEntity();
        entity.setId(loan.id().getValue());
        entity.setCustomerId(loan.application().getCustomerId());
        entity.setAccountId(loan.application().getAccountId());
        entity.setType(LoanTypeEntity.valueOf(loan.application().getType().name()));
        entity.setPrincipalAmount(loan.application().getPrincipal().getAmount());
        entity.setCurrency(loan.application().getPrincipal().getCurrency().getCurrencyCode());
        entity.setTermMonths(loan.application().getTermMonths());
        entity.setAnnualInterestRate(loan.application().getAnnualInterestRate());
        entity.setStatus(LoanStatusEntity.valueOf(loan.status().name()));
        entity.setAppliedAt(loan.appliedAt());
        try {
            entity.setScheduleJson(objectMapper.writeValueAsString(loan.schedule().getInstallments()));
        } catch (JsonProcessingException e) {
            entity.setScheduleJson("[]");
        }
        return entity;
    }

    public Loan toDomain(LoanEntity entity) {
        LoanId id = LoanId.of(entity.getId());
        Money principal = Money.of(entity.getPrincipalAmount(), entity.getCurrency());
        LoanApplication application = new LoanApplication(
                entity.getCustomerId(), entity.getAccountId(),
                LoanType.valueOf(entity.getType().name()), principal,
                entity.getTermMonths(), entity.getAnnualInterestRate()
        );
        LoanStatus status = LoanStatus.valueOf(entity.getStatus().name());

        List<Repayment> installments;
        try {
            Repayment[] arr = objectMapper.readValue(entity.getScheduleJson(), Repayment[].class);
            installments = Arrays.asList(arr);
        } catch (Exception e) {
            installments = Collections.emptyList();
        }
        RepaymentSchedule schedule = new RepaymentSchedule(installments);

        return Loan.reconstitute(id, application, status, schedule, entity.getAppliedAt());
    }
}