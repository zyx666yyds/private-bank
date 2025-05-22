package com.chinasoft.approvalservice.repository;

import com.chinasoft.approvalservice.model.entity.ApprovalFlow;
import com.chinasoft.approvalservice.model.entity.ApprovalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApprovalRecordRepository extends JpaRepository<ApprovalRecord, String> {
    List<ApprovalRecord> findByApprovalFlowAndIsDeletedFalse(ApprovalFlow approvalFlow);
    Optional<ApprovalRecord> findByApprovalFlowAndLevelAndIsDeletedFalse(ApprovalFlow approvalFlow, Integer level);
}
