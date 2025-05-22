package com.chinasoft.advisoryservice.controller;

import com.chinasoft.advisoryservice.model.dto.RecommendationDTO;
import com.chinasoft.advisoryservice.model.entity.Recommendation;
import com.chinasoft.advisoryservice.service.RecommendationService;
import com.chinasoft.bankcommon.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
