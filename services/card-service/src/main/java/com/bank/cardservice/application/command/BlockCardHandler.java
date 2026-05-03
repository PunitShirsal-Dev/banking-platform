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
public class BlockCardHandler {

    private static final JsonLogger log = JsonLogger.of(BlockCardHandler.class);
    private final CardDomainService domainService;
    private final CardEventPublisher eventPublisher;

    @Transactional
    public BankCard handle(BlockCardCommand command) {
        CardId cardId = CardId.of(UUID.fromString(command.getCardId()));
        BankCard card = domainService.blockCard(cardId);
        eventPublisher.publishCardBlocked(card);
        log.event("CardBlocked", card);
        return card;
    }
}
