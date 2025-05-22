package com.chinasoft.approvalservice.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
* 交易审批记录表
* @TableName approval_record
*/
@Entity
@Data
@ToString(exclude = {"approvalFlow","approver"})
public class ApprovalRecord  {

    /**
    * 审批记录ID，UUID格式
    */
    @Id
    @Column(name = "approval_id", length = 36)
    private String approvalId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "flow_id", referencedColumnName = "flow_id", nullable = false)
    private ApprovalFlow approvalFlow;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "approver_id", referencedColumnName = "user_id", nullable = false)
    private User approver;

    /**
    * 关联交易流水号
    */
    @NotBlank(message="[关联交易流水号]不能为空")
    @Size(max= 20,message="编码长度不能超过20")
    @Length(max= 20,message="编码长度不能超过20")
    private String tradeId;
    /**
    * 审批等级：1-客户经理审批，2-风控审批，3-合规审批
    */
    @Column(name = "level", nullable = false)
    private Integer level;

    /**
    * 审批决定：通过/拒绝
    */
    @Column(name = "decision", length = 10, nullable = false)
    private String decision;
    /**
    * 审批意见
    */
    @Column(name = "comment", columnDefinition = "text")
    private String comment;
    /**
    * 审批时间
    */
    private Date createdAt;
    /**
    * 删除状态 （0表示已删除，1标识存在）
    */
    @Column(name ="is_deleted",nullable = false)
    @Type(type = "org.hibernate.type.IntegerType")
    private Integer isDeleted;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 更新时间
    */
    private Date updateTime;

}
