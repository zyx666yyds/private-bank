package com.wealth.portfolioservice.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Map;
/**
 * @author:张宇阳Alan
 * @date:2025/5/22
 * @description:投资组合比例数据传输对象，包含投资组合中各类资产的比例信息
 */
@Data
public class PortfolioRatioDTO {
    private Map<String, BigDecimal> typeRatio;    // 资产类型比例
    private Map<String, BigDecimal> topProducts;  // 主要产品比例
}    