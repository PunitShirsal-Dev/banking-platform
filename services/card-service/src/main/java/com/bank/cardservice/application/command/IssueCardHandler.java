package com.bank.cardservice.application.command;

import com.bank.cardservice.domain.model.*;
import com.bank.cardservice.domain.port.CardEventPublisher;
import com.bank.cardservice.domain.service.CardDomainService;
import com.bank.cardservice.shared.JsonLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IssueCardHandler {

    private static final JsonLogger log = JsonLogger.of(IssueCardHandler.class);
    private final CardDomainService domainService;
    private final CardEventPublisher eventPublisher;

    @Transactional
    public BankCard handle(IssueCardCommand command) {
        CardId id = CardId.random();
        CardNumber cardNumber = CardNumber.of(generateCardNumber()); // simplified
        ExpiryDate expiry = ExpiryDate.fromNow(36); // 3 years
        String cvv = "000"; // in real life, generate & encrypt
        CardType type = CardType.valueOf(command.getCardType().toUpperCase());

        BankCard card = domainService.issueCard(id, command.getCustomerId(), command.getAccountId(),
                type, cardNumber, expiry, cvv);
        eventPublisher.publishCardIssued(card);
        log.event("CardIssued", card);
        return card;
    }

    private String generateCardNumber() {
        // Simplified; normally call a card network service like Visa/Mastercard
        return "411111" + UUID.randomUUID().toString().substring(0, 10);
    }
}