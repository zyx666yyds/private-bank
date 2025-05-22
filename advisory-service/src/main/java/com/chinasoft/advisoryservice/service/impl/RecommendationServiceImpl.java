package com.chinasoft.advisoryservice.service.impl;

import cn.hutool.json.JSONUtil;
import com.chinasoft.advisoryservice.enums.TradeType;
import com.chinasoft.advisoryservice.model.dto.RecommendationDTO;
import com.chinasoft.advisoryservice.model.dto.TradeDTO;
import com.chinasoft.advisoryservice.model.entity.Recommendation;
import com.chinasoft.advisoryservice.model.entity.RecommendationItem;
import com.chinasoft.advisoryservice.repository.RecommendationRepository;
import com.chinasoft.advisoryservice.repository.RecommendedItemRepository;
import com.chinasoft.advisoryservice.service.RecommendationService;
import com.chinasoft.bankcommon.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;

    private final RecommendedItemRepository recommendedItemRepository;

    private final RestTemplate restTemplate;

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
        // 客户采纳推荐
        Optional<Recommendation> optional = recommendationRepository.findById(recommendationId);
        Recommendation recommendation = optional.orElseThrow(() -> new RuntimeException("推荐不存在"));
        recommendation.setAccepted(true);
        recommendationRepository.save(recommendation);

        //生成交易
        List<RecommendationItem> items = recommendedItemRepository.findByRecommendationId(recommendationId);
        for (RecommendationItem item : items) {
            TradeDTO tradeDTO = new TradeDTO();
            tradeDTO.setClientId(recommendation.getClientId());
            tradeDTO.setAmount(item.getAmount());
            tradeDTO.setProductCode(item.getProductCode());
            tradeDTO.setType(TradeType.SUBSCRIBE);
            System.out.println(JSONUtil.parse(tradeDTO));
            restTemplate.postForObject("http://localhost:9011/trades", JSONUtil.parse(tradeDTO), BaseResponse.class);
        }
    }

    @Override
    public void updateRecommendationFeedback(String recommendationId, String feedback) {
        Recommendation recommendation = recommendationRepository.findById(recommendationId).orElseThrow(() -> new RuntimeException("推荐不存在"));
        recommendation.setFeedback(feedback);
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
