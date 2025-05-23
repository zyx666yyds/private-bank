package com.wealth.portfolioservice.entity;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @author:张宇阳Alan
 * @date:2025/5/22
 * @description:投资组合项实体类,对应数据库中的portfolio表，存储投资组合中的具体资产项目
 */
@Entity
@Table(name = "portfolio_item")
@EntityListeners(AuditingEntityListener.class)
@Data
public class PortfolioItem {
    
    @Id
    @Column(length = 32)
    private String itemId;                        // 投资组合项ID，唯一标识
    
    @Column(length = 20, nullable = false)
    private String portfolioId;                   // 所属投资组合ID
    
    @Column(length = 20, nullable = false)
    private String productCode;                     // 产品代码

    @Column(length = 100, nullable = false)
    private String productName;                      // 产品名称
    
    @Column(length = 20, nullable = false)
    private String type;                              // 产品类型(基金、股票、债券等)
    
    @Column(precision = 20, scale = 2, nullable = false)
    private BigDecimal amount;                         // 持有数量
    
    @Column(precision = 20, scale = 4, nullable = false)
    private BigDecimal unitValue;                      // 单位净值
    
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdAt;                              // 创建时间
}    