package com.chinasoft.authservice.enums;

import lombok.Getter;

/**
 * 客户风险承受能力等级
 */
@Getter
public enum RiskLevel {
    /**
     * 保守型
     */
    CONSERVATIVE("保守型"),
    /**
     * 平衡型
     */
    BALANCED("平衡型"),
    /**
     * 进取型
     */
    AGGRESSIVE("进取型");

    private final String description;

    RiskLevel(String description) {
        this.description = description;
    }
}