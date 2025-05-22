package com.chinasoft.notificationservice.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;

/**
* 系统通知表
* @TableName notification
*/
@Data
@Entity
@Table(name = "notification")
public class Notification implements Serializable {

    /**
    * 通知ID，格式NOTIF-yyyyMMdd-nnnnnn或UUID
    */
    @Id
    @NotBlank(message="[通知ID，格式NOTIF-yyyyMMdd-nnnnnn或UUID]不能为空")
    @Size(max= 32,message="编码长度不能超过32")
    @Length(max= 32,message="编码长度不能超过32")
    private String notificationId;
    /**
    * 接收人客户编号
    */
    @NotBlank(message="[接收人客户编号]不能为空")
    @Size(max= 18,message="编码长度不能超过18")
    @Length(max= 18,message="编码长度不能超过18")
    @Column(name = "client_id")
    private String clientId;
    /**
    * 通知类型：交易状态/审批结果/KYC提醒等
    */
    @NotBlank(message="[通知类型：交易状态/审批结果/KYC提醒等]不能为空")
    @Size(max= 30,message="编码长度不能超过30")
    @Length(max= 30,message="编码长度不能超过30")
    @Column(name = "type")
    private String type;
    /**
    * 通知正文内容
    */
    @NotBlank(message="[通知正文内容]不能为空")
    @Size(max= -1,message="编码长度不能超过-1")
    @Length(max= -1,message="编码长度不能超过-1")
    @Column(name = "content")
    private String content;
    /**
    * 是否已读
    */
    @Column(name = "read")
    private Integer read;
    /**
    * 通知创建时间
    */
    @Column(name = "created_at")
    @CreatedDate //@CreatedDate 注解会自动填充创建时间
    private LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);// 截断到秒;
    /**
    * 通知更新时间
    */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    /**
    * 删除状态 （0表示已删除，1标识存在）
    */
    @Column(name = "is_deleted")
    @NotNull(message="[删除状态 （1表示已删除，0标识存在）]不能为空")
    private Integer isDeleted;

}
