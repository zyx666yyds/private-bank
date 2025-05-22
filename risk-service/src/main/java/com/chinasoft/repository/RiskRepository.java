package com.chinasoft.repository;

import com.chinasoft.model.domain.RiskAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RiskRepository extends JpaRepository<RiskAssessment,String> {

    RiskAssessment findTopByClientIdOrderByCreatedAtDesc(@Param("clientId") String clientId);

    List<RiskAssessment> findByClientIdAndCreatedAtAfter(@Param("clientId")String clientId, @Param("dateTime") LocalDateTime dateTime);
}
