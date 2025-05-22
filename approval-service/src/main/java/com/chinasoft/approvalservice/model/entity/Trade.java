package com.chinasoft.approvalservice.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

/**
* 交易记录表
* @TableName trade
*/

@Data
@Entity
public class Trade implements Serializable {

    /**
    * 交易流水号，格式TyyyyMMddnnnnnn，如T2025032300123
    */
    @Id
    @NotBlank(message="[交易流水号，格式TyyyyMMddnnnnnn，如T2025032300123]不能为空")
    @Size(max= 20,message="编码长度不能超过20")
    @Length(max= 20,message="编码长度不能超过20")
    @Column(name ="trade_id")
    private String tradeId;

    /**
    * 客户编号
    */
    @NotBlank(message="[客户编号]不能为空")
    @Size(max= 18,message="编码长度不能超过18")
    @Length(max= 18,message="编码长度不能超过18")
    private String clientId;
    /**
    * 产品编号，调仓交易可能为空
    */
    @Size(max= 20,message="编码长度不能超过20")
    @Length(max= 20,message="编码长度不能超过20")
    private String productCode;
    /**
    * 交易类型：申购/赎回/调仓
    */
    @NotBlank(message="[交易类型：申购/赎回/调仓]不能为空")
    @Size(max= 20,message="编码长度不能超过20")
    @Length(max= 20,message="编码长度不能超过20")
    private String type;
    /**
    * 交易金额或数量
    */
    @NotNull(message="[交易金额或数量]不能为空")
    private BigDecimal amount;
    /**
    * 交易状态：待审批/审批中/已拒绝/已执行/失败
    */
    @NotBlank(message="[交易状态：待审批/审批中/已拒绝/已执行/失败]不能为空")
    @Size(max= 20,message="编码长度不能超过20")
    @Length(max= 20,message="编码长度不能超过20")
    private String status;
    /**
    * 交易创建时间
    */
    private Date createTime;
    /**
    * 交易状态更新时间
    */
    private Date updateTime;
    /**
    * 删除状态 （0表示未删除，1标识已删除）
    */
    @Column(name ="is_deleted")
    @Type(type = "org.hibernate.type.IntegerType")
    @NotNull(message="[删除状态 （0表示未删除，1标识已删除）]不能为空")
    private Integer isDeleted;


}
