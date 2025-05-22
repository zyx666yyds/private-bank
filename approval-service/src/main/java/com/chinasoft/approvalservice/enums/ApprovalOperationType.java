package com.chinasoft.approvalservice.enums;

public enum ApprovalOperationType {
    SUBMIT("提交"),
    APPROVE("审批通过"),
    REJECT("审批拒绝"),
    SEND_BACK("驳回"),
    RESUBMIT("重新提交"),
    TERMINATE("终止");
    
    private final String description;
    
    ApprovalOperationType(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}