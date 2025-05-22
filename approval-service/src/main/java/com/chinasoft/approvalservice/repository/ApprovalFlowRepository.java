package com.chinasoft.approvalservice.repository;

import com.chinasoft.approvalservice.model.entity.ApprovalFlow;
import com.chinasoft.approvalservice.model.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApprovalFlowRepository extends JpaRepository<ApprovalFlow, String> {
    Optional<ApprovalFlow> findByTradeAndIsDeletedFalse(Trade trade);
    List<ApprovalFlow> findByStatusAndIsDeletedFalse(String status);
    List<ApprovalFlow> findByCurrentLevelAndStatusAndIsDeletedFalse(Integer currentLevel, String status);
}
