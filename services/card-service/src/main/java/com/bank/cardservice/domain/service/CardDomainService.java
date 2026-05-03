package com.bank.cardservice.domain.service;


import com.bank.cardservice.domain.model.*;
import com.bank.cardservice.domain.port.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CardDomainService {

    private final CardRepository cardRepository;

    public BankCard issueCard(CardId id, String customerId, String accountId, CardType type,
                          CardNumber cardNumber, ExpiryDate expiry, String cvv) {
        BankCard card = BankCard.issue(id, customerId, accountId, type, cardNumber, expiry, cvv);
        cardRepository.save(card);
        return card;
    }

    public BankCard findById(CardId id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("BankCard not found"));
    }

    public BankCard activateCard(CardId id) {
        BankCard card = findById(id);
        card.activate();
        cardRepository.save(card);
        return card;
    }

    public BankCard blockCard(CardId id) {
        BankCard card = findById(id);
        card.block();
        cardRepository.save(card);
        return card;
    }

    public BankCard closeCard(CardId id) {
        BankCard card = findById(id);
        card.close();
        cardRepository.save(card);
        return card;
    }

    public BankCard updateLimits(CardId id, CardLimit newLimits) {
        BankCard card = findById(id);
        card.updateLimits(newLimits);
        cardRepository.save(card);
        return card;
    }
}