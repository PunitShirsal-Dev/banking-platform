package com.bank.cardservice.infrastructure.web;

import com.bank.cardservice.application.command.*;
import com.bank.cardservice.application.dto.*;
import com.bank.cardservice.application.query.CardDetailsQuery;
import com.bank.cardservice.domain.model.BankCard;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardController {

    private final IssueCardHandler issueHandler;
    private final ActivateCardHandler activateHandler;
    private final BlockCardHandler blockHandler;
    private final CardDetailsQuery detailsQuery;

    @PostMapping
    public ResponseEntity<CardResponseDTO> issue(
            @RequestHeader("X-Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody IssueCardRequestDTO request) {
        IssueCardCommand cmd = new IssueCardCommand(
                request.getCustomerId(), request.getAccountId(),
                request.getCardType(), idempotencyKey);
        BankCard bankCard = issueHandler.handle(cmd);
        return ResponseEntity.ok(toDto(bankCard));
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<CardResponseDTO> activate(
            @RequestHeader("X-Idempotency-Key") String idempotencyKey,
            @PathVariable String id) {
        BankCard bankCard = activateHandler.handle(new ActivateCardCommand(id, idempotencyKey));
        return ResponseEntity.ok(toDto(bankCard));
    }

    @PostMapping("/{id}/block")
    public ResponseEntity<CardResponseDTO> block(
            @RequestHeader("X-Idempotency-Key") String idempotencyKey,
            @PathVariable String id) {
        BankCard bankCard = blockHandler.handle(new BlockCardCommand(id, idempotencyKey));
        return ResponseEntity.ok(toDto(bankCard));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardResponseDTO> getCard(@PathVariable String id) {
        CardResponseDTO dto = detailsQuery.getCardDetails(id);
        return ResponseEntity.ok(dto);
    }

    private CardResponseDTO toDto(BankCard c) {
        return new CardResponseDTO(
                c.id().toString(), c.customerId(), c.accountId(),
                c.type().name(), c.cardNumber().getMasked(),
                c.expiryDate().getValue().toString(), c.status().name());
    }
}