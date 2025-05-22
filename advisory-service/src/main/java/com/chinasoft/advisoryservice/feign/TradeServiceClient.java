package com.chinasoft.advisoryservice.feign;

import com.chinasoft.advisoryservice.model.dto.TradeDTO;
import com.chinasoft.bankcommon.common.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "trade-service")
public interface TradeServiceClient {

    @PostMapping("/trades")
    BaseResponse createTrade(@Valid @RequestBody TradeDTO tradeDTO);
}
