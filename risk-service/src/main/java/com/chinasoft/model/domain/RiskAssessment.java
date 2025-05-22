package com.chinasoft.model.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
* 客户风险评估表
* @TableName risk_assessment
*/
@Data
@Entity
@Table(name = "risk_assessment", indexes = {
    @Index(name = "idx_client_created", columnList = "client_id, created_at")
})
public class RiskAssessment implements Serializable {

    /**
    * 风控评估编号，格式RISK-yyyyMMdd-clientId，如RISK-20250323-100000001
    */
    @Id
    @NotBlank(message="[风控评估编号，格式RISK-yyyyMMdd-clientId，如RISK-20250323-100000001]不能为空")
    @Size(max= 32,message="编码长度不能超过32")
    @Length(max= 32,message="编码长度不能超过32")
    private String riskId;

    /**
    * 客户编号
    */
    @NotBlank(message="[客户编号]不能为空")
    @Size(max= 18,message="编码长度不能超过18")
    @Length(max= 18,message="编码长度不能超过18")
    @Column(name = "client_id")
    private String clientId;

    /**
    * 风控人员编号
    */
    @NotBlank(message="[风控人员编号]不能为空")
    @Size(max= 12,message="编码长度不能超过12")
    @Length(max= 12,message="编码长度不能超过12")
    @Column(name = "evaluator_id")
    private String evaluatorId;

    /**
    * 风险得分，0-100分
    */
    @NotNull(message="[风险得分，0-100分]不能为空")
    @Column(name = "score")
    private Integer score;

    /**
    * 评估结果等级：保守型/稳健型/进取型
    */
    @NotBlank(message="[评估结果等级：保守型/稳健型/进取型]不能为空")
    @Size(max= 20,message="编码长度不能超过20")
    @Length(max= 20,message="编码长度不能超过20")
    @Column(name = "result_level")
    private String resultLevel;

    /**
    * 评估时间
    */
    @CreatedDate //@CreatedDate 注解会自动填充创建时间
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);// 截断到秒

    /**
    * 附注说明
    */
    @Size(max= 500,message="编码长度不能超过500")
    @Length(max= 500,message="编码长度不能超过500")
    @Column(name = "remarks")
    private String remarks;

    /**
    * 更新评估时间
    */
    @LastModifiedDate //@LastModifiedDate 注解会自动填充更新时间
    @Column(name = "updated_at",columnDefinition = "datetime")
    private LocalDateTime updatedAt;

    /**
    * 删除状态 （0表示已删除，1标识存在）
    */
    @Column(name = "is_deleted", columnDefinition = "tinyint(1) default 0")
    private Integer isDeleted = 0; // 默认值


}
