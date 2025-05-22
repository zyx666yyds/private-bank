package com.chinasoft.tradeservice.controller;

import com.chinasoft.bankcommon.common.BaseResponse;
import com.chinasoft.bankcommon.common.ResultUtils;
import com.chinasoft.tradeservice.model.dto.TradeDTO;
import com.chinasoft.tradeservice.model.entity.Trade;
import com.chinasoft.tradeservice.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    @PostMapping("/trades")
    public BaseResponse createTrade(@Valid @RequestBody TradeDTO tradeDTO) {
        Trade trade = tradeService.createTrade(tradeDTO);
        return new BaseResponse(200, trade);
    }

    @GetMapping("/trades")
    public BaseResponse queryTrades(@RequestParam String clientId,
                                    @RequestParam String status) {
        return new BaseResponse(200, tradeService.queryTrades(clientId, status));
    }

    @PutMapping("/trades")
    public BaseResponse updateTradeStatus(@RequestBody Trade trade) {
        tradeService.updateTrade(trade);
        return ResultUtils.success(null);
    }
}
