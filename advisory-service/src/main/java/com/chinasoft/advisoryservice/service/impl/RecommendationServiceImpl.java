package com.chinasoft.advisoryservice.service.impl;

import com.chinasoft.advisoryservice.model.dto.RecommendationDTO;
import com.chinasoft.advisoryservice.model.entity.Recommendation;
import com.chinasoft.advisoryservice.model.entity.RecommendationItem;
import com.chinasoft.advisoryservice.repository.RecommendationRepository;
import com.chinasoft.advisoryservice.repository.RecommendedItemRepository;
import com.chinasoft.advisoryservice.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;

    private final RecommendedItemRepository recommendedItemRepository;

    @Override
    public void createRecommendation(RecommendationDTO recommendationDTO) {
        Recommendation recommendation = new Recommendation();
        recommendation.setRecommendationId(generateRecommendationId(recommendationDTO.getClientId()));
        recommendation.setClientId(recommendationDTO.getClientId());
        recommendation.setAdvisorId(recommendationDTO.getAdvisorId());
        recommendation.setDeleted(false);
        List<RecommendationItem> items = recommendationDTO.getItems();
        recommendationRepository.save(recommendation);
        for (RecommendationItem item : items) {
            item.setItemId(UUID.randomUUID().toString());
            item.setRecommendationId(recommendation.getRecommendationId());
            item.setDeleted(false);
            recommendedItemRepository.save(item);
        }
    }

    @Override
    public void adoptRecommendation(String recommendationId) {
        Optional<Recommendation> optional = recommendationRepository.findById(recommendationId);
        Recommendation recommendation = optional.orElseThrow(() -> new RuntimeException("推荐不存在"));
        recommendation.setAccepted(true);
        recommendationRepository.save(recommendation);
    }

    private String generateRecommendationId(String clientId) {
        UUID uuid = UUID.randomUUID();
        String string = uuid.toString().substring(0, 4);
        StringBuilder builder = new StringBuilder("RE-");
        builder.append(clientId).append("-").append(string);
        return new String(builder);
    }
}
