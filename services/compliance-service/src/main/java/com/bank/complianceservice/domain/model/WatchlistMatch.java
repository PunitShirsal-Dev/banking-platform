package com.bank.complianceservice.domain.model;

import lombok.Value;

@Value
public class WatchlistMatch {
    String listName;      // e.g., OFAC, EU Sanctions
    String matchedField;
    String matchedValue;
    String severity;      // HIGH, MEDIUM, LOW
}