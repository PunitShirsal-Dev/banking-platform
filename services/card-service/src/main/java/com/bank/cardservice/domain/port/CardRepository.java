package com.bank.cardservice.domain.port;

import com.bank.cardservice.domain.model.BankCard;
import com.bank.cardservice.domain.model.CardId;
import java.util.Optional;

public interface CardRepository {
    void save(BankCard bankCard);
    Optional<BankCard> findById(CardId id);
}