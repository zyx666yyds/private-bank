package com.chinasoft.tradeservice.service.impl;

import com.chinasoft.tradeservice.enums.TradeStatus;
import com.chinasoft.tradeservice.repository.TradeRepository;
import com.chinasoft.tradeservice.model.dto.TradeDTO;
import com.chinasoft.tradeservice.model.entity.Trade;
import com.chinasoft.tradeservice.enums.TradeType;
import com.chinasoft.tradeservice.service.TradeService;
import com.chinasoft.tradeservice.utils.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;

    private final IdGenerator idGenerator;

    @Override
    public Trade createTrade(TradeDTO tradeDTO) {
        if (tradeDTO.getType() == TradeType.REDEEM && tradeDTO.getAmount().signum() <= 0) {
            throw new RuntimeException("金额必须为正数");
        }
        Trade trade = new Trade();
        trade.setTradeId(idGenerator.generateTradeId());
        trade.setClientId(tradeDTO.getClientId());
        trade.setProductCode(tradeDTO.getProductCode());
        trade.setType(tradeDTO.getType());
        trade.setAmount(tradeDTO.getAmount());
        trade.setStatus(TradeStatus.PENDING);
        Trade saved = tradeRepository.save(trade);
        trade.setDeleted(false);
        return saved;
    }

    @Override
    public List<Trade> queryTrades(String clientId, String status) {
        return tradeRepository.findByClientIdAndStatusOrderByCreateTimeDesc(clientId, TradeStatus.valueOf(status));
    }

    @Override
    public void updateTrade(Trade updateTrade) {
        if (null == updateTrade.getTradeId() || updateTrade.getTradeId().isEmpty()) {
            throw new RuntimeException("tradeId不能为空");
        }
        Trade trade = tradeRepository.findById(updateTrade.getTradeId()).orElseThrow(() -> new RuntimeException("tradeId不存在"));
        trade.setStatus(updateTrade.getStatus());
        tradeRepository.save(trade);
    }
}
