package com.chinasoft.approvalservice.controller;

import com.chinasoft.approvalservice.model.entity.*;
import com.chinasoft.approvalservice.repository.ApprovalFlowRepository;
import com.chinasoft.approvalservice.repository.TradeRepository;
import com.chinasoft.approvalservice.repository.UserRepository;
import com.chinasoft.approvalservice.service.ApprovalService;
import com.chinasoft.bankcommon.common.BaseResponse;
import com.chinasoft.bankcommon.common.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 24088
 * @version 1.0
 * @date 2025/5/13 013 15:06
 */
@RestController
public class ApprovalController {

    @Autowired
    private ApprovalService approvalService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TradeRepository tradeRepository;
    @Autowired
    private ApprovalFlowRepository approvalFlowRepository;

    @PostMapping("/submit/{tradeId}")
    public BaseResponse<ApprovalFlow> submitForApproval(@PathVariable String tradeId,
                                               @RequestParam String submitterId) {
        Trade trade = tradeRepository.findById(tradeId).get();
        User submitter = userRepository.findById(submitterId).get();

        ApprovalFlow flow = approvalService.submitForApproval(trade, submitter);
        return ResultUtils.success(flow);
    }

    @PostMapping("/approve/{flowId}")
    public BaseResponse<Boolean> approve(@PathVariable String flowId,
                                     @RequestParam String approverId,
                                     @RequestParam(required = false) String comment) {
        ApprovalFlow flow = approvalFlowRepository.findById(flowId)
                .orElseThrow(() -> new RuntimeException("Flow not found"));
        User approver = userRepository.findById(approverId).get();
        approvalService.approve(flow, approver, comment);
        return ResultUtils.success(true);
    }

    @PostMapping("/reject/{flowId}")
    public BaseResponse<Boolean> reject(@PathVariable String flowId,
                                     @RequestParam String approverId,
                                     @RequestParam(required = false) String comment) {
        ApprovalFlow flow = approvalFlowRepository.findById(flowId).get();
        User approver = userRepository.findById(approverId).get();

        approvalService.reject(flow, approver, comment);
        return ResultUtils.success(true);
    }

    @GetMapping("/flow/{flowId}")
    public BaseResponse<ApprovalFlow> getApprovalFlow(@PathVariable String flowId) {
        ApprovalFlow flow = approvalFlowRepository.findById(flowId).get();
        return ResultUtils.success(flow);
    }

    @GetMapping("/flow/{flowId}/records")
    public BaseResponse<List<ApprovalRecord>> getApprovalRecords(@PathVariable String flowId) {
        ApprovalFlow flow = approvalFlowRepository.findById(flowId).get();
        List<ApprovalRecord> records = approvalService.getApprovalRecords(flow);
        return ResultUtils.success(records);
    }

    @GetMapping("/flow/{flowId}/logs")
    public BaseResponse<List<ApprovalOperationLog>> getOperationLogs(@PathVariable String flowId) {
        ApprovalFlow flow = approvalFlowRepository.findById(flowId).get();
        List<ApprovalOperationLog> logs = approvalService.getOperationLogs(flow);
        return ResultUtils.success(logs);
    }

}
