package com.bank.cardservice.application.command;

import com.bank.cardservice.domain.model.BankCard;
import com.bank.cardservice.domain.model.CardId;
import com.bank.cardservice.domain.port.CardEventPublisher;
import com.bank.cardservice.domain.service.CardDomainService;
import com.bank.cardservice.shared.JsonLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActivateCardHandler {

    private static final JsonLogger log = JsonLogger.of(ActivateCardHandler.class);
    private final CardDomainService domainService;
    private final CardEventPublisher eventPublisher;

    @Transactional
    public BankCard handle(ActivateCardCommand command) {
        CardId cardId = CardId.of(UUID.fromString(command.getCardId()));
        BankCard card = domainService.activateCard(cardId);
        eventPublisher.publishCardActivated(card);
        log.event("CardActivated", card);
        return card;
    }
}