package com.chinasoft.tradeservice.model.dto;

import com.chinasoft.tradeservice.enums.TradeType;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class TradeDTO {
    @NotBlank(message = "客户ID不能为空")
    @Size(max = 18, message = "客户ID长度超限")
    private String clientId;

    @NotBlank(message = "产品编号不能为空")
    @Size(max = 20, message = "产品编号长度超限")
    private String productCode;

    @NotNull(message = "交易类型不能为空")
    private TradeType type;

    @DecimalMin(value = "0.01", message = "金额必须大于0")
    @Digits(integer = 18, fraction = 2, message = "金额格式错误")
    private BigDecimal amount;
}
