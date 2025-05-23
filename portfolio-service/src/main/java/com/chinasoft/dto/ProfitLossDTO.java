package com.wealth.portfolioservice.dto;

import lombok.Data;
import java.math.BigDecimal;
/**
 * @author:张宇阳Alan
 * @date:2025/5/22
 * @description:封装收益与亏损数据
 */
@Data   //@date注解自动生成getter，setter等方法
public class ProfitLossDTO {
    private BigDecimal totalProfit;      //总收益
    private BigDecimal profitRate;       //收益比率
    private BigDecimal dailyProfit;      //当日收益
    private BigDecimal dailyProfitRate;  //当日收益比率
}    