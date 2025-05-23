package com.wealth.portfolioservice.dto;

import lombok.Data;
import java.math.BigDecimal;
/**
 * @author:张宇阳Alan
 * @date:2025/5/22
 * @description:封装组合再平衡操作的数据传输对象
 */
@Data
public class RebalanceActionDTO {
    private String productCode;              //产品代码
    private String productName;              //产品名称
    private String type;                     //产品类型
    private BigDecimal currentAmount;        //当前金额
    private BigDecimal targetAmount;         //目标金额
    private BigDecimal adjustAmount;         //调整金额
    private String actionType;               //操作类型，买入或卖出
}    