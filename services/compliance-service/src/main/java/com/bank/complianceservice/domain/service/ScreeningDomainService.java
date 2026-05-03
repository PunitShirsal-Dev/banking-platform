package com.bank.complianceservice.domain.service;

import com.bank.complianceservice.domain.model.*;
import com.bank.complianceservice.domain.port.ScreeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ScreeningDomainService {

    private final WatchListService watchlistService;
    private final ScreeningRepository repository;

    public ScreeningCase screenCustomer(CustomerSnapshot customer) {
        List<WatchlistMatch> matches = watchlistService.checkAgainstWatchLists(customer);
        ScreeningResult result = watchlistService.determineResult(matches);
        ScreeningCase sc = ScreeningCase.create(ScreeningId.random(), customer, result, matches);
        repository.save(sc);
        return sc;
    }

    public ScreeningCase findById(ScreeningId id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Screening case not found"));
    }

    public ScreeningCase startReview(ScreeningId id) {
        ScreeningCase sc = findById(id);
        sc.startReview();
        repository.save(sc);
        return sc;
    }

    public ScreeningCase closeClear(ScreeningId id) {
        ScreeningCase sc = findById(id);
        sc.closeClear();
        repository.save(sc);
        return sc;
    }

    public ScreeningCase escalate(ScreeningId id) {
        ScreeningCase sc = findById(id);
        sc.escalate();
        repository.save(sc);
        return sc;
    }
}