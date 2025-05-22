package com.chinasoft.approvalservice.service.impl;

import com.chinasoft.approvalservice.enums.ApprovalStatus;
import com.chinasoft.approvalservice.model.entity.*;
import com.chinasoft.approvalservice.repository.*;
import com.chinasoft.approvalservice.service.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ApprovalServiceImpl implements ApprovalService {
    private static final int[] APPROVAL_LEVELS = {1, 2, 3};
    
    @Autowired
    private TradeRepository tradeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApprovalFlowRepository approvalFlowRepository;
    @Autowired
    private ApprovalRecordRepository approvalRecordRepository;
    @Autowired
    private ApprovalOperationLogRepository operationLogRepository;
    @Autowired
    private ApprovalTimeoutMonitorRepository timeoutMonitorRepository;
    
    /**
     * 提交交易审批
     */
    public ApprovalFlow submitForApproval(Trade trade, User submitter) {
        // 创建审批流程
        ApprovalFlow flow = new ApprovalFlow();
        flow.setFlowId(UUID.randomUUID().toString());
        flow.setTrade(trade);
        flow.setCurrentLevel(1); // 从第一级开始
        flow.setStatus(ApprovalStatus.IN_PROGRESS.getDescription());
        flow.setSubmitTime(new Date());
        flow.setCreator(submitter);
        flow.setCreateTime(new Date());
        flow.setUpdateTime(new Date());
        
        // 更新交易状态
        trade.setStatus(ApprovalStatus.IN_PROGRESS.getDescription());
        trade.setUpdateTime(new Date());
        tradeRepository.save(trade);
        
        // 保存流程
        approvalFlowRepository.save(flow);
        
        // 记录操作日志
        logOperation(flow, submitter, "提交", null, ApprovalStatus.IN_PROGRESS.getDescription());
        
        // 设置超时监控
        setupTimeoutMonitor(flow, 1);
        
        return flow;
    }
    
    /**
     * 审批操作
     */
    public void approve(ApprovalFlow flow, User approver, String comment) {
        int currentLevel = flow.getCurrentLevel();
        
        // 创建审批记录
        ApprovalRecord record = new ApprovalRecord();
        record.setApprovalId(UUID.randomUUID().toString());
        record.setApprovalFlow(flow);
        record.setLevel(currentLevel);
        record.setTradeId(flow.getTrade().getTradeId());
        record.setApprover(approver);
        record.setDecision("通过");
        record.setComment(comment);
        record.setIsDeleted(0);
        record.setCreatedAt(new Date());
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        approvalRecordRepository.save(record);
        
        // 更新超时监控
        updateTimeoutMonitor(flow, currentLevel);
        
        // 检查是否还有下一级审批
        if (currentLevel < APPROVAL_LEVELS.length) {
            // 进入下一级审批
            flow.setCurrentLevel(currentLevel + 1);
            flow.setUpdateTime(new Date());
            approvalFlowRepository.save(flow);
            
            // 记录操作日志
            logOperation(flow, approver, "审批通过", comment, flow.getStatus());
            
            // 设置下一级超时监控
            setupTimeoutMonitor(flow, currentLevel + 1);
        } else {
            // 审批完成
            completeApproval(flow, approver, ApprovalStatus.APPROVED.getDescription(), "审批通过");
        }
    }
    
    /**
     * 拒绝操作
     */
    public void reject(ApprovalFlow flow, User approver, String comment) {
        int currentLevel = flow.getCurrentLevel();
        
        // 创建审批记录
        ApprovalRecord record = new ApprovalRecord();
        record.setApprovalId(UUID.randomUUID().toString());
        record.setApprovalFlow(flow);
        record.setLevel(currentLevel);
        record.setTradeId(flow.getTrade().getTradeId());
        record.setApprover(approver);
        record.setDecision("拒绝");
        record.setIsDeleted(0);
        record.setComment(comment);
        record.setCreatedAt(new Date());
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        approvalRecordRepository.save(record);
        
        // 更新超时监控
        updateTimeoutMonitor(flow, currentLevel);
        
        // 审批终止
        completeApproval(flow, approver, ApprovalStatus.REJECTED.getDescription(), "审批拒绝");
    }
    
    /**
     * 驳回操作（驳回到上一级）
     */
    public void sendBack(ApprovalFlow flow, User operator, String comment) {
        int currentLevel = flow.getCurrentLevel();
        
        if (currentLevel <= 1) {
            throw new IllegalStateException("已经是第一级审批，无法驳回");
        }
        
        // 记录操作日志
        logOperation(flow, operator, "驳回", comment, flow.getStatus());
        
        // 回退到上一级
        flow.setCurrentLevel(currentLevel - 1);
        flow.setUpdateTime(new Date());
        approvalFlowRepository.save(flow);
        
        // 设置上一级超时监控
        setupTimeoutMonitor(flow, currentLevel - 1);
    }
    
    /**
     * 终止审批流程
     */
    public void terminate(ApprovalFlow flow, User operator, String comment) {
        completeApproval(flow, operator, ApprovalStatus.TERMINATED.getDescription(), "审批终止");
    }
    
    /**
     * 重新提交审批
     */
    public void resubmit(ApprovalFlow flow, User submitter, String comment) {
        // 重置为第一级审批
        flow.setCurrentLevel(1);
        flow.setStatus(ApprovalStatus.IN_PROGRESS.getDescription());
        flow.setUpdateTime(new Date());
        approvalFlowRepository.save(flow);
        
        // 更新交易状态
        Trade trade = flow.getTrade();
        trade.setStatus(ApprovalStatus.IN_PROGRESS.getDescription());
        trade.setUpdateTime(new Date());
        tradeRepository.save(trade);
        
        // 记录操作日志
        logOperation(flow, submitter, "重新提交", comment, ApprovalStatus.IN_PROGRESS.getDescription());
        
        // 设置超时监控
        setupTimeoutMonitor(flow, 1);
    }
    
//    /**
//     * 检查审批超时
//     */
//    @Scheduled(fixedRate = 3600000) // 每小时检查一次
//    public void checkApprovalTimeout() {
//        List<ApprovalTimeoutMonitor> timeouts = timeoutMonitorRepository
//            .findByExpectedCompleteTimeBeforeAndIsTimeoutFalse(new Date());
//
//        for (ApprovalTimeoutMonitor monitor : timeouts) {
//            monitor.setIsTimeout(true);
//            monitor.setTimeoutDuration((int) ((new Date().getTime() - monitor.getExpectedCompleteTime().getTime()) / (1000 * 60)));
//            timeoutMonitorRepository.save(monitor);
//
//            // 可以添加超时通知逻辑
//            // notifyTimeout(monitor);
//        }
//    }
    
    private void completeApproval(ApprovalFlow flow, User operator, String status, String operation) {
        flow.setStatus(status);
        flow.setCompleteTime(new Date());
        flow.setUpdateTime(new Date());
        approvalFlowRepository.save(flow);
        
        // 更新交易状态
        Trade trade = flow.getTrade();
        trade.setStatus(status);
        trade.setUpdateTime(new Date());
        tradeRepository.save(trade);
        
        // 记录操作日志
        logOperation(flow, operator, operation, null, status);
    }
    
    private void logOperation(ApprovalFlow flow, User operator, String operationType, 
                            String comment, String newStatus) {
        ApprovalOperationLog log = new ApprovalOperationLog();
        log.setLogId(UUID.randomUUID().toString());
        log.setApprovalFlow(flow);
        log.setOperationType(operationType);
        log.setOperator(operator);
        log.setOperationTime(new Date());
        log.setOperationComment(comment);
        log.setPreviousStatus(flow.getStatus());
        log.setCurrentStatus(newStatus);
        operationLogRepository.save(log);
    }
    
    private void setupTimeoutMonitor(ApprovalFlow flow, int level) {
        ApprovalTimeoutMonitor monitor = new ApprovalTimeoutMonitor();
        monitor.setMonitorId(UUID.randomUUID().toString());
        monitor.setApprovalFlow(flow);
        monitor.setCurrentLevel(level);
        
        // 设置预期完成时间（例如当前时间+24小时）
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 24);
        monitor.setExpectedCompleteTime(calendar.getTime());
        
        timeoutMonitorRepository.save(monitor);
    }
    
    private void updateTimeoutMonitor(ApprovalFlow flow, int level) {
        List<ApprovalTimeoutMonitor> monitors = timeoutMonitorRepository
            .findByApprovalFlowAndCurrentLevelAndIsTimeoutFalse(flow, level);
        
        if (!monitors.isEmpty()) {
            ApprovalTimeoutMonitor monitor = monitors.get(0);
            monitor.setActualCompleteTime(new Date());
            timeoutMonitorRepository.save(monitor);
        }
    }

    /**
     * 获取审批流程的所有审批记录
     */
    public List<ApprovalRecord> getApprovalRecords(ApprovalFlow flow) {
        return approvalRecordRepository.findByApprovalFlowAndIsDeletedFalse(flow);
    }

    /**
     * 获取审批流程的所有操作日志（按时间升序排列）
     */
    public List<ApprovalOperationLog> getOperationLogs(ApprovalFlow flow) {
        return operationLogRepository.findByApprovalFlowOrderByOperationTimeAsc(flow);
    }
}