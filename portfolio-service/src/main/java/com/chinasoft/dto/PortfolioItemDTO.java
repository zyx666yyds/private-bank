package com.wealth.portfolioservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @author:张宇阳Alan
 * @date:2025/5/22
 * @description:投资组合项数据传输对象，用于前端与后端之间的数据传输
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PortfolioItemDTO {
    private String itemId;
    private String productCode;
    private String productName;
    private String type;
    private BigDecimal amount;
    private BigDecimal unitValue;
    private BigDecimal marketValue;
    private Date createdAt;
    private BigDecimal proportion;
    private BigDecimal profitAmount;
    private BigDecimal profitRate;
}    