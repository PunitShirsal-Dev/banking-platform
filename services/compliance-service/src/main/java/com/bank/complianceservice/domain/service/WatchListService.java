package com.bank.complianceservice.domain.service;

import com.bank.complianceservice.domain.model.CustomerSnapshot;
import com.bank.complianceservice.domain.model.ScreeningResult;
import com.bank.complianceservice.domain.model.WatchlistMatch;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WatchListService {

    // In reality, this would call an external sanctions list API or use an embedded database
    public List<WatchlistMatch> checkAgainstWatchLists(CustomerSnapshot customer) {
        List<WatchlistMatch> matches = new ArrayList<>();

        // Simplified demo check – replace with real AML screening logic
        if (customer.getFirstName().toUpperCase().contains("OSAMA")) {
            matches.add(new WatchlistMatch("OFAC", "firstName", customer.getFirstName(), "HIGH"));
        }
        if (customer.getCountry().equalsIgnoreCase("IR")) {
            matches.add(new WatchlistMatch("EU_SANCTIONS", "country", customer.getCountry(), "HIGH"));
        }

        return matches;
    }

    public ScreeningResult determineResult(List<WatchlistMatch> matches) {
        if (matches.isEmpty()) return ScreeningResult.CLEAR;
        boolean hasHigh = matches.stream().anyMatch(m -> m.getSeverity().equals("HIGH"));
        return hasHigh ? ScreeningResult.HIT : ScreeningResult.MANUAL_REVIEW;
    }
}