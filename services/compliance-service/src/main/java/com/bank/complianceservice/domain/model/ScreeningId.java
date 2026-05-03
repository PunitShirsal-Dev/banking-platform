package com.bank.complianceservice.domain.model;

import lombok.Value;
import java.util.UUID;

@Value(staticConstructor = "of")
public class ScreeningId {
    UUID value;

    public static ScreeningId random() {
        return new ScreeningId(UUID.randomUUID());
    }
}