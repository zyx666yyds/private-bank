package com.chinasoft.advisoryservice.repository;

import com.chinasoft.advisoryservice.model.entity.RecommendationItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendedItemRepository extends JpaRepository<RecommendationItem,String> {
}
