package com.bank.cardservice.domain.model;

import lombok.Value;
import java.util.UUID;

@Value(staticConstructor = "of")
public class CardId {
    UUID value;

    public static CardId random() {
        return new CardId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}