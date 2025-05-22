package com.chinasoft.advisoryservice.repository;

import com.chinasoft.advisoryservice.model.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationRepository extends JpaRepository<Recommendation,String> {
}
