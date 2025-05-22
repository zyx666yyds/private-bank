package com.chinasoft.tradeservice.repository;

import com.chinasoft.tradeservice.enums.TradeStatus;
import com.chinasoft.tradeservice.model.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, String> {

    List<Trade> findByClientIdAndStatusOrderByCreateTimeDesc(
            @Param("clientId") String clientId,
            @Param("status") TradeStatus status);
}