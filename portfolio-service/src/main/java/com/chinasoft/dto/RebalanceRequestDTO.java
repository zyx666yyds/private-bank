package com.wealth.portfolioservice.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
/**
 * @author:张宇阳Alan
 * @date:2025/5/22
 * @description:再平衡请求数据传输对象，用于请求生成投资组合再平衡计划
 */
@Data
public class RebalanceRequestDTO {
    @NotNull(message = "投资组合ID不能为空")
    private String portfolioId;    // 投资组合ID
    
    @NotEmpty(message = "至少需要一个再平衡条件")
    private List<RebalanceConditionDTO> conditions;   // 再平衡条件
    
    @NotNull(message = "目标风险等级不能为空")
    private String targetRiskLevel;  // 目标风险等级
}    