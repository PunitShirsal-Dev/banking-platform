package com.bank.paymentgateway.infrastructure.persistence;

import com.bank.paymentgateway.domain.model.*;
import org.springframework.stereotype.Component;

@Component
public class PaymentEntityMapper {

    public PaymentOrderEntity toEntity(PaymentOrder order) {
        PaymentOrderEntity entity = new PaymentOrderEntity();
        entity.setId(order.id().getValue());
        entity.setSourceAccountId(order.sourceAccountId());
        entity.setBeneficiaryName(order.beneficiary().getName());
        entity.setIban(order.beneficiary().getIban());
        entity.setBic(order.beneficiary().getBic());
        entity.setBankName(order.beneficiary().getBankName());
        entity.setCountryCode(order.beneficiary().getCountryCode());
        entity.setAmount(order.amount().getAmount());
        entity.setCurrency(order.amount().getCurrency().getCurrencyCode());
        entity.setPaymentType(PaymentTypeEntity.valueOf(order.paymentType().name()));
        entity.setStatus(PaymentStatusEntity.valueOf(order.status().name()));
        entity.setReference(order.reference());
        entity.setCreatedAt(order.createdAt());
        return entity;
    }

    public PaymentOrder toDomain(PaymentOrderEntity entity) {
        PaymentId id = PaymentId.of(entity.getId());
        Beneficiary beneficiary = new Beneficiary(
                entity.getBeneficiaryName(), entity.getIban(), entity.getBic(),
                entity.getBankName(), entity.getCountryCode()
        );
        Money amount = Money.of(entity.getAmount(), entity.getCurrency());
        PaymentType paymentType = PaymentType.valueOf(entity.getPaymentType().name());
        PaymentStatus status = PaymentStatus.valueOf(entity.getStatus().name());

        PaymentOrder.GenericPayment genericPayment = new PaymentOrder.GenericPayment(
                entity.getSourceAccountId(),
                entity.getReference(),
                entity.getCreatedAt()
        );

        return PaymentOrder.reconstitute(id, beneficiary,
                amount, paymentType, status, genericPayment);
    }
}