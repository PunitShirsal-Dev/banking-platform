package com.bank.cardservice.domain.port;

import com.bank.cardservice.domain.model.BankCard;

public interface CardEventPublisher {
    void publishCardIssued(BankCard bankCard);
    void publishCardActivated(BankCard bankCard);
    void publishCardBlocked(BankCard bankCard);
    void publishCardClosed(BankCard bankCard);
}