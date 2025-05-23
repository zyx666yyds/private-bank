package com.wealth.portfolioservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
/**
 * @author:张宇阳Alan
 * @date:2025/5/22
 * @description:投资组合数据传输对象，用于前端与后端之间的数据传输
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PortfolioDTO {
    private String portfolioId;         // 投资组合ID
    private String clientId;            // 客户ID
    private String name;                // 投资组合名称
    private BigDecimal totalValue;      // 总价值
    private Date createdAt;             // 创建时间
    private Date updatedAt;             // 更新时间
    private List<PortfolioItemDTO> items;  // 投资组合项列表
    private PortfolioRatioDTO ratio;    // 投资组合比例
    private ProfitLossDTO profitLoss;   // 盈亏信息
}    