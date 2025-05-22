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

    @Column()
    private String clientId;

    @Column()
    private String productCode;

    @Enumerated(EnumType.STRING)
    @Column()
    private TradeType type;

    @Column()
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column()
    private TradeStatus status;

    @CreationTimestamp
    private LocalDateTime createTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;

    @Column(name = "is_deleted")
    private boolean deleted;
}