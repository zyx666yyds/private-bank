package com.chinasoft.notificationservice.model.dto;

import lombok.Data;

@Data
public class TradeNotification {
    private String clientId;
    private String tradeId;
    private String status;
    private String productCode;
    private String amount;
}
