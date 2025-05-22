package com.chinasoft.approvalservice.repository;

import com.chinasoft.approvalservice.model.entity.ApprovalFlow;
import com.chinasoft.approvalservice.model.entity.ApprovalTimeoutMonitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprovalTimeoutMonitorRepository extends JpaRepository<ApprovalTimeoutMonitor, String> {
    List<ApprovalTimeoutMonitor> findByApprovalFlowAndCurrentLevelAndIsTimeoutFalse(ApprovalFlow approvalFlow, Integer currentLevel);
}
