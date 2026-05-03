package com.bank.notificationservice.infrastructure.persistence;

import com.bank.notificationservice.domain.model.Notification;
import com.bank.notificationservice.domain.model.NotificationId;
import com.bank.notificationservice.domain.port.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NotificationPersistenceAdapter implements NotificationRepository {

    private final NotificationJpaRepository jpaRepository;
    private final NotificationEntityMapper mapper;

    @Override
    @Transactional
    public void save(Notification notification) {
        jpaRepository.save(mapper.toEntity(notification));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Notification> findById(NotificationId id) {
        return jpaRepository.findById(id.getValue()).map(mapper::toDomain);
    }
}