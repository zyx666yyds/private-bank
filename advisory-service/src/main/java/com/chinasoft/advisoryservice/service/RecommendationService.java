package com.chinasoft.advisoryservice.service;

import com.chinasoft.advisoryservice.model.dto.RecommendationDTO;

public interface RecommendationService {
    void createRecommendation(RecommendationDTO recommendationDTO);

    void adoptRecommendation(String recommendationId);

    void updateRecommendationFeedback(String recommendationId, String feedback);
}
