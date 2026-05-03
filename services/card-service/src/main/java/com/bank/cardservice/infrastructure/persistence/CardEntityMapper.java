package com.bank.cardservice.infrastructure.persistence;

import com.bank.cardservice.domain.model.*;
import org.springframework.stereotype.Component;

import java.time.YearMonth;
import java.util.Currency;

@Component
public class CardEntityMapper {

    public BankCardEntity toEntity(BankCard card) {
        BankCardEntity entity = new BankCardEntity();
        entity.setId(card.id().getValue());
        entity.setCustomerId(card.customerId());
        entity.setAccountId(card.accountId());
        entity.setType(CardTypeEntity.valueOf(card.type().name()));
        entity.setCardNumberFull(card.cardNumber().getFull());
        entity.setCardNumberMasked(card.cardNumber().getMasked());
        entity.setExpiryDate(card.expiryDate().getValue().toString());
        entity.setCvvHash(card.cvv()); // store hashed in real life
        entity.setStatus(CardStatusEntity.valueOf(card.status().name()));
        entity.setDailyLimit(card.limits().getDailyLimit());
        entity.setTransactionLimit(card.limits().getTransactionLimit());
        entity.setLimitCurrency(card.limits().getCurrency().getCurrencyCode());
        entity.setIssuedAt(card.issuedAt());
        return entity;
    }

    public BankCard toDomain(BankCardEntity entity) {
        CardId id = CardId.of(entity.getId());
        CardNumber number = CardNumber.of(entity.getCardNumberFull()); // full not masked
        ExpiryDate expiry = ExpiryDate.of(YearMonth.parse(entity.getExpiryDate()));
        CardLimit limits = new CardLimit(
                entity.getDailyLimit(),
                entity.getTransactionLimit(),
                Currency.getInstance(entity.getLimitCurrency())
        );

        BankCard.GenericBankCard genericBankCard = new BankCard.GenericBankCard(
                entity.getCustomerId(),
                entity.getAccountId(),
                entity.getCvvHash(),
                expiry,
                entity.getIssuedAt()
        );

        CardType cardType = mapType(entity.getType());
        CardStatus cardStatus = mapStatus(entity.getStatus());

        return BankCard.reconstitute(id, cardType, number, cardStatus, limits, genericBankCard);
    }

    private CardType mapType(CardTypeEntity type) {
        return switch (type) {
            case DEBIT  -> CardType.DEBIT;
            case CREDIT -> CardType.CREDIT;
        };
    }

    private CardStatus mapStatus(CardStatusEntity status) {
        return switch (status) {
            case ISSUED  -> CardStatus.ISSUED;
            case ACTIVE  -> CardStatus.ACTIVE;
            case BLOCKED -> CardStatus.BLOCKED;
            case CLOSED  -> CardStatus.CLOSED;
        };
    }
}