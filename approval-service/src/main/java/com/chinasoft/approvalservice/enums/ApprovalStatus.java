package com.chinasoft.approvalservice.enums;

import lombok.Getter;

@Getter
public enum ApprovalStatus {
    PENDING("待审批"),
    IN_PROGRESS("审批中"),
    APPROVED("已通过"),
    REJECTED("已拒绝"),
    TERMINATED("已终止");
    
    private final String description;
    
    ApprovalStatus(String description) {
        this.description = description;
    }

}