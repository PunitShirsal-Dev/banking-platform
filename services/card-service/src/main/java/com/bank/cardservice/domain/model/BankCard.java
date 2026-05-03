package com.bank.cardservice.domain.model;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@Accessors(fluent = true)
public class BankCard {
    private CardId id;
    private String customerId;
    private String accountId;               // linked bank account
    private CardType type;
    private CardNumber cardNumber;
    private ExpiryDate expiryDate;
    private String cvv;                     // would be tokenised/hashed
    private CardStatus status;
    private CardLimit limits;
    private Instant issuedAt;

    private BankCard() {}

    public static BankCard issue(CardId id, String customerId, String accountId, CardType type,
                                 CardNumber cardNumber, ExpiryDate expiry, String cvv) {
        BankCard bankCard = new BankCard();
        bankCard.id = id;
        bankCard.customerId = customerId;
        bankCard.accountId = accountId;
        bankCard.type = type;
        bankCard.cardNumber = cardNumber;
        bankCard.expiryDate = expiry;
        bankCard.cvv = cvv;
        bankCard.status = CardStatus.ISSUED;
        bankCard.limits = CardLimit.defaultLimits();
        bankCard.issuedAt = Instant.now();
        return bankCard;
    }

    public void activate() {
        if (status != CardStatus.ISSUED) throw new IllegalStateException("Only issued cards can be activated");
        status = CardStatus.ACTIVE;
    }

    public void block() {
        status = CardStatus.BLOCKED;
    }

    public void close() {
        status = CardStatus.CLOSED;
    }

    public void updateLimits(CardLimit newLimits) {
        this.limits = newLimits;
    }

    public record GenericBankCard(String customerId,
                                  String accountId,
                                  String cvv,
                                  ExpiryDate expiryDate,
                                  Instant issuedAt) {}

    // Reconstitution
    public static BankCard reconstitute(CardId id,
                                        CardType type,
                                        CardNumber cardNumber,
                                        CardStatus status,
                                        CardLimit limits,
                                        GenericBankCard genericBankCard) {
        BankCard bankCard = new BankCard();
        bankCard.id = id;
        bankCard.customerId = genericBankCard.customerId;
        bankCard.accountId = genericBankCard.accountId;
        bankCard.type = type;
        bankCard.cardNumber = cardNumber;
        bankCard.expiryDate = genericBankCard.expiryDate;
        bankCard.cvv = genericBankCard.cvv;
        bankCard.status = status;
        bankCard.limits = limits;
        bankCard.issuedAt = genericBankCard.issuedAt;
        return bankCard;
    }
}