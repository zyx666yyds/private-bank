package com.chinasoft.approvalservice.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "approval_operation_log")
@ToString(exclude = {"approvalFlow","operator"})
public class ApprovalOperationLog {
    @Id
    @Column(name = "log_id", length = 36)
    private String logId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "flow_id", referencedColumnName = "flow_id", nullable = false)
    private ApprovalFlow approvalFlow;
    
    @Column(name = "operation_type", length = 20, nullable = false)
    private String operationType;

    @ManyToOne
    @JoinColumn(name = "operator_id", referencedColumnName = "user_id", nullable = false)
    private User operator;
    
    @Column(name = "operation_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date operationTime;
    
    @Column(name = "operation_comment", columnDefinition = "text")
    private String operationComment;
    
    @Column(name = "previous_status", length = 20)
    private String previousStatus;
    
    @Column(name = "current_status", length = 20)
    private String currentStatus;
    
}