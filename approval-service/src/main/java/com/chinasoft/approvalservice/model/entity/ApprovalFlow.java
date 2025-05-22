package com.chinasoft.approvalservice.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
@Entity
@Data
@Table(name = "approval_flow")
public class ApprovalFlow implements Serializable {
    @Id
    @Column(name = "flow_id", length = 36)
    private String flowId;

    @OneToOne
    @JoinColumn(name = "trade_id", referencedColumnName = "trade_id", nullable = false)
    private Trade trade;
    
    @Column(name = "current_level", nullable = false)
    private Integer currentLevel;
    
    @Column(name = "status", length = 20, nullable = false)
    private String status;
    
    @Column(name = "submit_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date submitTime;
    
    @Column(name = "complete_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date completeTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id", referencedColumnName = "user_id", nullable = false)
    private User creator;
    
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;
    
    @Column(name = "create_time", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    
    @OneToMany(mappedBy = "approvalFlow", cascade = CascadeType.ALL)
    private List<ApprovalRecord> approvalRecords = new ArrayList<>();
    
    @OneToMany(mappedBy = "approvalFlow", cascade = CascadeType.ALL)
    private List<ApprovalOperationLog> operationLogs = new ArrayList<>();
    
}