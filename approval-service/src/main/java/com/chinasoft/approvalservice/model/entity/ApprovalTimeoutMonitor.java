package com.chinasoft.approvalservice.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "approval_timeout_monitor")
public class ApprovalTimeoutMonitor {
    @Id
    @Column(name = "monitor_id", length = 36)
    private String monitorId;
    
    @ManyToOne
    @JoinColumn(name = "flow_id", referencedColumnName = "flow_id", nullable = false)
    private ApprovalFlow approvalFlow;
    
    @Column(name = "current_level", nullable = false)
    private Integer currentLevel;
    
    @Column(name = "expected_complete_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expectedCompleteTime;
    
    @Column(name = "actual_complete_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actualCompleteTime;
    
    @Column(name = "is_timeout")
    private Boolean isTimeout = false;
    
    @Column(name = "timeout_duration")
    private Integer timeoutDuration;

}