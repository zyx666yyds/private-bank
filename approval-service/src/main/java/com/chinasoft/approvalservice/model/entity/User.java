package com.chinasoft.approvalservice.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

/**
* 系统用户表
* @TableName user
*/
@Data
@Entity
public class User implements Serializable {

    /**
    * 用户ID，12位，前缀u，如U00012345678
    */
    @Id
    @NotBlank(message="[用户ID，12位，前缀u，如U00012345678]不能为空")
    @Size(max= 12,message="编码长度不能超过12")
    @Length(max= 12,message="编码长度不能超过12")
    @Column(name ="user_id")
    private String userId;
    /**
    * 登录用户名，唯一
    */
    @NotBlank(message="[登录用户名，唯一]不能为空")
    @Size(max= 50,message="编码长度不能超过50")
    @Length(max= 50,message="编码长度不能超过50")
    private String username;
    /**
    * 加密后的密码
    */
    @NotBlank(message="[加密后的密码]不能为空")
    @Size(max= 100,message="编码长度不能超过100")
    @Length(max= 100,message="编码长度不能超过100")
    private String password;
    /**
    * 用户角色：客户/客户经理/风控人员/合规人员/管理员
    */
    @NotBlank(message="[用户角色：客户/客户经理/风控人员/合规人员/管理员]不能为空")
    @Size(max= 30,message="编码长度不能超过30")
    @Length(max= 30,message="编码长度不能超过30")
    private String role;
    /**
    * 状态：正常/禁用/锁定
    */
    @NotBlank(message="[状态：正常/禁用/锁定]不能为空")
    @Size(max= 10,message="编码长度不能超过10")
    @Length(max= 10,message="编码长度不能超过10")
    private String status;
    /**
    * 最后登录时间
    */
    private Date lastLogin;
    /**
    * 创建时间
    */
    private Date createdAt;
    /**
    * 更新时间
    */
    private Date updatedAt;
    /**
    * 删除状态 （0表示已删除，1标识存在）
    */
    @Column(name ="is_deleted")
    @Type(type = "org.hibernate.type.IntegerType")
    @NotNull(message="[删除状态 （0表示已删除，1标识存在）]不能为空")
    private Integer isDeleted;


}
