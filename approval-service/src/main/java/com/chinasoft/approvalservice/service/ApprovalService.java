package com.chinasoft.approvalservice.service;

import com.chinasoft.approvalservice.model.entity.*;

import java.util.List;

public interface ApprovalService {
    
    /**
     * 提交交易审批
     */
     ApprovalFlow submitForApproval(Trade trade, User submitter);
    
    /**
     * 审批操作
     */
    void approve(ApprovalFlow flow, User approver, String comment) ;

    
    /**
     * 拒绝操作
     */
    void reject(ApprovalFlow flow, User approver, String comment);

    
    /**
     * 驳回操作（驳回到上一级）
     */
    void sendBack(ApprovalFlow flow, User operator, String comment);
    
    /**
     * 终止审批流程
     */
    void terminate(ApprovalFlow flow, User operator, String comment);
    
    /**
     * 重新提交审批
     */
    void resubmit(ApprovalFlow flow, User submitter, String comment);


    /**
     * 获取审批流程的所有审批记录
     */
    List<ApprovalRecord> getApprovalRecords(ApprovalFlow flow);
    /**
     * 获取审批流程的所有操作日志（按时间升序排列）
     */
    List<ApprovalOperationLog> getOperationLogs(ApprovalFlow flow);
}