package com.bank.transactionservice.infrastructure.persistence;

import com.bank.transactionservice.domain.model.*;
import org.springframework.stereotype.Component;

@Component
public class TransferEntityMapper {

    public TransferEntity toEntity(TransferAggregate transfer) {
        TransferEntity entity = new TransferEntity();
        entity.setId(transfer.id().getValue());
        entity.setSourceAccountId(transfer.details().getSourceAccountId().getValue().toString());
        entity.setTargetAccountId(transfer.details().getTargetAccountId().getValue().toString());
        entity.setAmount(transfer.details().getAmount().getAmount());
        entity.setCurrency(transfer.details().getAmount().getCurrency().getCurrencyCode());
        entity.setDescription(transfer.details().getDescription());
        entity.setState(TransferStateEntity.valueOf(transfer.state().name()));
        entity.setCreatedAt(transfer.createdAt());
        entity.setUpdatedAt(transfer.updatedAt());
        return entity;
    }

    public TransferAggregate toDomain(TransferEntity entity) {
        AccountId source = AccountId.of(java.util.UUID.fromString(entity.getSourceAccountId()));
        AccountId target = AccountId.of(java.util.UUID.fromString(entity.getTargetAccountId()));
        Money amount = Money.of(entity.getAmount(), entity.getCurrency());
        TransferDetails details = new TransferDetails(source, target, amount, entity.getDescription());
        TransferState state = TransferState.valueOf(entity.getState().name());
        return TransferAggregate.reconstitute(
                TransactionId.of(entity.getId()),
                details,
                state,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}