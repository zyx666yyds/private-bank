package com.wealth.portfolioservice.entity;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @author:张宇阳Alan
 * @date:2025/5/22
 * @description:投资组合实体类,对应数据库中的portfolio表，存储投资组合基本信息
 */
@Entity
@Table(name = "portfolio")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Portfolio {
    
    @Id
    @Column(length = 20)
    private String portfolioId;  // 投资组合ID，唯一标识
    
    @Column(length = 18, nullable = false)
    private String clientId;     // 客户ID，关联客户表

    @Column(length = 50, nullable = false)
    private String name;          // 投资组合名称
    
    @Column(precision = 20, scale = 2, nullable = false)
    private BigDecimal totalValue;      // 投资组合总价值
    
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdAt;               // 创建时间
    
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @Column(nullable = false)
    private Date updatedAt;             //更新时间
}    