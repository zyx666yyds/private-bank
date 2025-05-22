package com.chinasoft.notificationservice.model.dto;

import lombok.Data;

@Data
public class KycNotification {
    private String clientId;
    private String riskLevel;
    private String message;
}
