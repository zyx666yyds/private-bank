package com.chinasoft.notificationservice.service;

import com.chinasoft.notificationservice.model.domain.Notification;

import java.util.List;

public interface NotificationService {
    List<Notification> getNotifications(String clientId, Boolean unread);
}
