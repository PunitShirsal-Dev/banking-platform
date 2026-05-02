package com.bank.accountservice.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
public class AccountEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String customerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountTypeEntity type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatusEntity status;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal balanceAmount;

    @Column(nullable = false, length = 3)
    private String balanceCurrency;

    @Column(nullable = false)
    private Instant openedAt;

    private Instant closedAt;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<TransactionLogEntity> transactionLogs = new ArrayList<>();

    public void addTransactionLog(TransactionLogEntity log) {
        log.setAccount(this);
        this.transactionLogs.add(log);
    }
}