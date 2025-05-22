package com.chinasoft.approvalservice.model.entity;

import lombok.Data;
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
public class ApprovalRecord implements Serializable {

    /**
    * 审批记录ID，UUID格式
    */
    @Id
    @NotBlank(message="[审批记录ID，UUID格式]不能为空")
    @Size(max= 36,message="编码长度不能超过36")
    @Length(max= 36,message="编码长度不能超过36")
    @Column(name = "approval_id")
    private String approvalId;

    @ManyToOne
    @JoinColumn(name = "flow_id", referencedColumnName = "flow_id", nullable = false)
    private ApprovalFlow approvalFlow;

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
    @NotNull(message="[审批等级：1-客户经理审批，2-风控审批，3-合规审批]不能为空")
    private Integer level;
//    /**
//    * 审批人编号
//    */
//    @NotBlank(message="[审批人编号]不能为空")
//    @Size(max= 12,message="编码长度不能超过12")
//    @Length(max= 12,message="编码长度不能超过12")
//    @Column(name ="approver_id")
//    private String approverId;
    /**
    * 审批决定：通过/拒绝
    */
    @NotBlank(message="[审批决定：通过/拒绝]不能为空")
    @Size(max= 10,message="编码长度不能超过10")
    @Length(max= 10,message="编码长度不能超过10")
    private String decision;
    /**
    * 审批意见
    */
    @Size(max= 300,message="编码长度不能超过-1")
    @Length(max= 300,message="编码长度不能超过-1")
    private String comment;
    /**
    * 审批时间
    */
    private Date createdAt;
    /**
    * 删除状态 （0表示已删除，1标识存在）
    */
    @Column(name ="is_deleted")
    @Type(type = "org.hibernate.type.IntegerType")
    @NotNull(message="[删除状态 （0表示未删除，1标识已删除）]不能为空")
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
