package com.bank.loanservice.domain.model;

import lombok.Value;
import java.util.List;

@Value
public class RepaymentSchedule {
    List<Repayment> installments;
}