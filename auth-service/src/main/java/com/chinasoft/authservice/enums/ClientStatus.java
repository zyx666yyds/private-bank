package com.chinasoft.authservice.enums;

import lombok.Getter;

/**
 * 客户账户状态枚举
 */
@Getter
public enum ClientStatus {
    /**
     * 活跃状态
     */
    NORMAL("正常"),
    /**
     * 账户冻结状态
     */
    FROZEN("冻结"),
    /**
     * 账户失效状态
     */
    DEACTIVATED("失效");

    private final String description;

    ClientStatus(String description) {
        this.description = description;
    }
}