package com.chinasoft.approvalservice.repository;

import com.chinasoft.approvalservice.model.entity.ApprovalFlow;
import com.chinasoft.approvalservice.model.entity.ApprovalOperationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprovalOperationLogRepository extends JpaRepository<ApprovalOperationLog, String> {
    List<ApprovalOperationLog> findByApprovalFlowOrderByOperationTimeAsc(ApprovalFlow approvalFlow);
}
