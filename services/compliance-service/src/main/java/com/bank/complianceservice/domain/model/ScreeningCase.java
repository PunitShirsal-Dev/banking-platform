package com.bank.complianceservice.domain.model;

import lombok.Getter;
import lombok.experimental.Accessors;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Accessors(fluent = true)
public class ScreeningCase {
    private ScreeningId id;
    private CustomerSnapshot customer;
    private ScreeningResult result;
    private List<WatchlistMatch> matches = new ArrayList<>();
    private CaseStatus caseStatus;
    private Instant screenedAt;

    private ScreeningCase() {}

    public static ScreeningCase create(ScreeningId id, CustomerSnapshot customer, ScreeningResult result,
                                       List<WatchlistMatch> matches) {
        ScreeningCase sc = new ScreeningCase();
        sc.id = id;
        sc.customer = customer;
        sc.result = result;
        sc.matches = new ArrayList<>(matches);
        sc.caseStatus = result == ScreeningResult.CLEAR ? CaseStatus.CLOSED_CLEAR : CaseStatus.OPEN;
        sc.screenedAt = Instant.now();
        return sc;
    }

    public void startReview() {
        if (caseStatus != CaseStatus.OPEN) throw new IllegalStateException("Cannot review non-open case");
        caseStatus = CaseStatus.UNDER_REVIEW;
    }

    public void closeClear() {
        caseStatus = CaseStatus.CLOSED_CLEAR;
    }

    public void escalate() {
        caseStatus = CaseStatus.CLOSED_ESCALATED;
    }

    // Reconstitution
    public static ScreeningCase reconstitute(ScreeningId id, CustomerSnapshot customer, ScreeningResult result,
                                             List<WatchlistMatch> matches, CaseStatus caseStatus, Instant screenedAt) {
        ScreeningCase sc = new ScreeningCase();
        sc.id = id;
        sc.customer = customer;
        sc.result = result;
        sc.matches = new ArrayList<>(matches);
        sc.caseStatus = caseStatus;
        sc.screenedAt = screenedAt;
        return sc;
    }

    public List<WatchlistMatch> matches() {
        return Collections.unmodifiableList(matches);
    }
}