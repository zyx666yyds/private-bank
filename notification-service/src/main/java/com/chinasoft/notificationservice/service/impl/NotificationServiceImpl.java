package com.chinasoft.notificationservice.service.impl;

import com.chinasoft.notificationservice.model.domain.Notification;
import com.chinasoft.notificationservice.repository.NotificationRepository;
import com.chinasoft.notificationservice.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private NotificationRepository repository;
    @Override
    public List<Notification> getNotifications(String clientId, Boolean unread) {
        if(unread!=null){
            return repository.findByClientIdAndRead(clientId, !unread);  // 返回未读消息
        }
        return repository.findByClientId(clientId); // 返回所有消息
    }
}
