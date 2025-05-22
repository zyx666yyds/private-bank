package com.chinasoft.advisoryservice.model.entity;

import com.chinasoft.bankcommon.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "recommended_item")
@Data
public class RecommendationItem {
    
    @Id
    @Column(name = "item_id")
    private String itemId;

    @Column(name = "recommendation_id")
    private String recommendationId;
    
    @Column(name = "product_code")
    private String productCode;
    
    @Column(name = "amount")
    private BigDecimal amount;
    
    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updateAt;

    @Column(name = "is_deleted")
    private boolean deleted;
}