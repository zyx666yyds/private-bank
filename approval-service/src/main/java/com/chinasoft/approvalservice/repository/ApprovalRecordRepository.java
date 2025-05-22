package com.chinasoft.approvalservice.repository;

import com.chinasoft.approvalservice.model.entity.ApprovalFlow;
import com.chinasoft.approvalservice.model.entity.ApprovalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApprovalRecordRepository extends JpaRepository<ApprovalRecord, String> {
    //    List<ApprovalRecord> findByApprovalFlowAndIsDeletedFalse(ApprovalFlow approvalFlow);
    @Query("SELECT r FROM ApprovalRecord r WHERE r.approvalFlow = :flow AND r.isDeleted = 0")
    List<ApprovalRecord> findByApprovalFlowAndIsDeletedFalse(@Param("flow") ApprovalFlow flow);

    Optional<ApprovalRecord> findByApprovalFlowAndLevelAndIsDeletedFalse(ApprovalFlow approvalFlow, Integer level);
}
