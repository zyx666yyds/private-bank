package com.wealth.portfolioservice.dto;

import lombok.Data;
import java.util.List;
/**
 * @author:张宇阳Alan
 * @date:2025/5/22
 * @description:封装投资组合数据传输对象
 */
@Data
public class PortfolioDraftDTO {
    private String clientId;                           //客户id
    private String advisorId;                          //顾问id
    private String name;                               //组合名称
    private List<PortfolioItemDTO> recommendedItems;   //推荐项列表
    private String riskLevel;                          //风险等级
    private String recommendationReason;               //推荐理由
}    