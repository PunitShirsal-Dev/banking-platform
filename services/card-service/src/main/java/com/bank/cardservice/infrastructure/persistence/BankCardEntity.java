package com.bank.cardservice.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "card")
@Getter
@Setter
@NoArgsConstructor
public class BankCardEntity {

    @Id
    private UUID id;
    private String customerId;
    private String accountId;

    @Enumerated(EnumType.STRING)
    private CardTypeEntity type;

    private String cardNumberFull;     // encrypted
    private String cardNumberMasked;
    private String expiryDate;
    private String cvvHash;

    @Enumerated(EnumType.STRING)
    private CardStatusEntity status;

    private BigDecimal dailyLimit;
    private BigDecimal transactionLimit;
    private String limitCurrency;

    private Instant issuedAt;
}