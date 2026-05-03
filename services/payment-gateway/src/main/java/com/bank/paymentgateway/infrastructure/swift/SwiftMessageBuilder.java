package com.bank.paymentgateway.infrastructure.swift;

import com.bank.paymentgateway.domain.model.PaymentOrder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class SwiftMessageBuilder {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyMMdd");

    /**
     * Builds an MT103 string representation (simplified).
     * Example fields: Sender, Receiver (beneficiary), Amount, Currency, Reference, etc.
     */
    public String buildMT103(PaymentOrder order) {
        // In a real bank, this would be a complex assembly using the SWIFT FIN format.
        // For demonstration, we emit a structured string that a real connector would parse.
        String date = LocalDate.now().format(DATE_FORMAT);
        return """
        {1:F21%s%sXXXX%s%s}{2:O103%s%s%s%s}{3:{108:%s}}{4:
        :20:%s
        :23B:CRED
        :32A:%s%s%s
        :50K:%s
        :59:/%s
        %s
        :71A:SHA
        -}"""
                .formatted(
                        order.sourceAccountId().substring(0, Math.min(12, order.sourceAccountId().length())),
                        date,
                        order.beneficiary().getBankName(),
                        order.beneficiary().getBic(),
                        date,
                        order.id().toString(),
                        order.amount().getCurrency().getCurrencyCode(),
                        order.amount().getAmount().toString(),
                        date,
                        order.id().toString(),
                        date,
                        order.amount().getCurrency().getCurrencyCode(),
                        order.amount().getAmount().toString(),
                        order.sourceAccountId(),
                        order.beneficiary().getIban(),
                        order.beneficiary().getName()
                );
    }
}