package com.bank.paymentgateway.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "payment_order")
@Getter
@Setter
@NoArgsConstructor
public class PaymentOrderEntity {

    @Id
    private UUID id;
    private String sourceAccountId;

    private String beneficiaryName;
    private String iban;
    private String bic;
    private String bankName;
    private String countryCode;

    private BigDecimal amount;
    private String currency;

    @Enumerated(EnumType.STRING)
    private PaymentTypeEntity paymentType;

    @Enumerated(EnumType.STRING)
    private PaymentStatusEntity status;

    private String reference;
    private Instant createdAt;
}