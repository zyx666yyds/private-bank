package com.chinasoft.advisoryservice.repository;

import com.chinasoft.advisoryservice.model.entity.RecommendationItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendedItemRepository extends JpaRepository<RecommendationItem,String> {

    List<RecommendationItem> findByRecommendationId(String recommendationId);
}
