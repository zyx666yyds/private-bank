package com.wealth.portfolioservice.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
/**
 * @author:张宇阳Alan
 * @date:2025/5/22
 * @description:再平衡计划数据传输对象，包含投资组合再平衡的建议方案
 */
@Data
public class RebalancePlanDTO {
    private String portfolioId;  // 投资组合ID
    private String clientId;  // 客户ID
    private BigDecimal currentTotalValue;  // 当前总价值
    private String targetRiskLevel;  // 目标风险等级
    private List<RebalanceActionDTO> actions;  // 再平衡操作列表
    private BigDecimal estimatedFees;  // 预估费用
    private String recommendation;  // 建议说明
}    