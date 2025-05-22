package com.chinasoft.tradeservice.service;

import com.chinasoft.tradeservice.model.dto.TradeDTO;
import com.chinasoft.tradeservice.model.entity.Trade;

import java.util.List;

public interface TradeService {
    Trade createTrade(TradeDTO request);

    List<Trade> queryTrades(String clientId, String status);

    void updateTrade(Trade trade);
}
