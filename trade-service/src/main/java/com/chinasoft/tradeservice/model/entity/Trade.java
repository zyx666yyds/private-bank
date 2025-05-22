package com.chinasoft.tradeservice.model.entity;

import com.chinasoft.tradeservice.enums.TradeStatus;
import com.chinasoft.tradeservice.enums.TradeType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "trade")
@Data
public class Trade {
    @Id
    private String tradeId;

    @Column(nullable = false, length = 18)
    private String clientId;

    @Column(nullable = false, length = 20)
    private String productCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TradeType type;

    @Column(nullable = false, precision = 20, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TradeStatus status;

    @CreationTimestamp
    private LocalDateTime createTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;

    @Column(name = "is_deleted")
    private boolean deleted;
}