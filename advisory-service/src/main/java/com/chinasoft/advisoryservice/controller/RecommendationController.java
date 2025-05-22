package com.chinasoft.advisoryservice.controller;

import com.chinasoft.advisoryservice.model.dto.RecommendationDTO;
import com.chinasoft.advisoryservice.service.RecommendationService;
import com.chinasoft.bankcommon.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;

    @PostMapping("/recommendations")
    public BaseResponse createRecommendation(@RequestBody RecommendationDTO recommendationDTO) {
        recommendationService.createRecommendation(recommendationDTO);
        return new BaseResponse<>(200,null);
    }

    @PostMapping("/recommendations/{recommendationId}/accept")
    public BaseResponse adoptRecommendation(@PathVariable String recommendationId) {
        recommendationService.adoptRecommendation(recommendationId);
        return new BaseResponse<>(200,null);
    }

    @PutMapping("/recommendations/{recommendationId}/response")
    public BaseResponse recordCustomerResponse(@PathVariable String recommendationId, @RequestBody String feedback) {
        recommendationService.updateRecommendationFeedback(recommendationId, feedback);
        return new BaseResponse<>(200,null);
    }
}
