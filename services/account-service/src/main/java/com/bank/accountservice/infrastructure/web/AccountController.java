package com.bank.accountservice.infrastructure.web;

import com.bank.accountservice.application.command.CreditAccountCommand;
import com.bank.accountservice.application.command.CreditAccountHandler;
import com.bank.accountservice.application.command.DebitAccountCommand;
import com.bank.accountservice.application.command.DebitAccountHandler;
import com.bank.accountservice.application.dto.AccountResponseDTO;
import com.bank.accountservice.application.dto.CreditRequestDTO;
import com.bank.accountservice.application.dto.DebitRequestDTO;
import com.bank.accountservice.application.query.AccountBalanceQuery;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final DebitAccountHandler debitHandler;
    private final CreditAccountHandler creditHandler;
    private final AccountBalanceQuery balanceQuery;

    @PostMapping("/{id}/debit")
    public ResponseEntity<Void> debit(@RequestHeader("X-Idempotency-Key") String idempotencyKey,
                                      @PathVariable String id,
                                      @Valid @RequestBody DebitRequestDTO request) {
        debitHandler.handle(new DebitAccountCommand(
                id, request.getAmount(), request.getCurrency(),
                request.getDescription(), idempotencyKey));
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/{id}/credit")
    public ResponseEntity<Void> credit(@RequestHeader("X-Idempotency-Key") String idempotencyKey,
                                       @PathVariable String id,
                                       @Valid @RequestBody CreditRequestDTO request) {
        creditHandler.handle(new CreditAccountCommand(
                id, request.getAmount(), request.getCurrency(),
                request.getDescription(), idempotencyKey));
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<AccountResponseDTO> getBalance(@PathVariable String id) {
        BigDecimal balance = balanceQuery.getBalance(id);
        // In real implementation, fetch full account via repository
        return ResponseEntity.ok(new AccountResponseDTO(id, null, null, null, balance.toString(), "USD"));
    }
}