package com.bank.accountservice.domain.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Accessors(fluent = true)
public class Account {
    private AccountId id;
    private String customerId;
    private AccountType type;
    private Money balance;
    private AccountStatus status;
    private Instant openedAt;
    @Setter(AccessLevel.PACKAGE)
    private Instant closedAt;
    private final List<TransactionLog> transactionLogs = new ArrayList<>();

    // Private no‑arg constructor for internal use
    private Account() {}

    /**
     * Factory for opening a brand-new account.
     */
    public static Account open(AccountId id, String customerId, AccountType type, Money initialDeposit) {
        Account account = new Account();
        account.id = id;
        account.customerId = customerId;
        account.type = type;
        account.balance = initialDeposit;
        account.status = AccountStatus.OPEN;
        account.openedAt = Instant.now();
        account.appendLog(new TransactionLog(TransactionType.CREDIT, initialDeposit, "Initial deposit"));
        return account;
    }

    /**
     * Factory for reconstituting an existing account from persistence.
     * This is PUBLIC so the infrastructure layer can call it.
     */

    public record GenericAccountArg(String customerId,
                                    Instant openedAt,
                                    Instant closedAt
    ) {}

    public static Account reconstitute(
            AccountId id,
            AccountType type,
            Money balance,
            AccountStatus status,
            List<TransactionLog> logs,
            GenericAccountArg genericAccountArg
            ) {

        Account account = new Account();
        account.id = id;
        account.customerId = genericAccountArg.customerId;
        account.type = type;
        account.balance = balance;
        account.status = status;
        account.openedAt = genericAccountArg.openedAt;
        account.closedAt = genericAccountArg.closedAt;
        logs.forEach(account::appendLog);
        return account;
    }

    public void debit(Money amount, String description) {
        if (this.status != AccountStatus.OPEN) throw new IllegalStateException("Account not open");
        if (this.balance.lessThan(amount)) throw new InsufficientFundsException(this.id, amount);
        this.balance = this.balance.subtract(amount);
        appendLog(new TransactionLog(TransactionType.DEBIT, amount, description));
    }

    public void credit(Money amount, String description) {
        if (this.status != AccountStatus.OPEN) throw new IllegalStateException("Account not open");
        this.balance = this.balance.add(amount);
        appendLog(new TransactionLog(TransactionType.CREDIT, amount, description));
    }

    // Package‑private for internal use
    Account setStatus(AccountStatus status) { this.status = status; return this; }
    Account setOpenedAt(Instant openedAt) { this.openedAt = openedAt; return this; }
    void appendLog(TransactionLog log) { this.transactionLogs.add(log); }

    public List<TransactionLog> transactionLogs() {
        return Collections.unmodifiableList(transactionLogs);
    }
}