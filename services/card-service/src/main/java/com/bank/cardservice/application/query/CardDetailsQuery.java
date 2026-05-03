package com.bank.cardservice.application.query;

import com.bank.cardservice.application.dto.CardResponseDTO;
import com.bank.cardservice.domain.model.BankCard;
import com.bank.cardservice.domain.model.CardId;
import com.bank.cardservice.domain.port.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardDetailsQuery {

    private final CardRepository repository;

    public CardResponseDTO getCardDetails(String cardId) {
        BankCard card = repository.findById(CardId.of(UUID.fromString(cardId)))
                .orElseThrow(() -> new IllegalArgumentException("BankCard not found"));
        return toDto(card);
    }

    private CardResponseDTO toDto(BankCard c) {
        return new CardResponseDTO(
                c.id().toString(),
                c.customerId(),
                c.accountId(),
                c.type().name(),
                c.cardNumber().getMasked(),
                c.expiryDate().getValue().toString(),
                c.status().name()
        );
    }
}