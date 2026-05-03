package com.bank.loanservice.domain.model;

import lombok.Value;
import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class Repayment {
    LocalDate dueDate;
    BigDecimal amount;
    boolean paid;
}