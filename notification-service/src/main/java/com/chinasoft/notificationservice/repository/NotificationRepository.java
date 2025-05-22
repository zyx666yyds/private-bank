package com.chinasoft.notificationservice.repository;

import com.chinasoft.notificationservice.model.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, String> {
    List<Notification> findByClientIdAndRead(String clientId, boolean b);

    List<Notification> findByClientId(String clientId);
}
