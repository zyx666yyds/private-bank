package com.wealth.portfolioservice.dto;

import lombok.Data;
/**
 * @author:张宇阳Alan
 * @date:2025/5/22
 * @description:封装投资组合再平衡条件的数据传输对象
 */
@Data
public class RebalanceConditionDTO {
    private String type;                //类型
    private Double targetPercentage;    //目标百分比
    private Double tolerance;           //容差
}    