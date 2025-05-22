package com.chinasoft.authservice.enums;

import lombok.Getter;

/**
 * 性别类型枚举
 */
@Getter
public enum Gender {
    /**
     * 男性
     */
    M("M"),
    /**
     * 女性
     */
    F("F");

    private final String description;

    Gender(String description) {
        this.description = description;
    }
}