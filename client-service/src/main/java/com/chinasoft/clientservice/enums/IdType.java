package com.chinasoft.clientservice.enums;

import lombok.Getter;

/**
 * 证件类型枚举（处理原始数据中的空格为下划线）
 */
@Getter
public enum IdType {
    /**
     * 身份证
     */
    ID_CARD("身份证"),
    /**
     * 护照
     */
    PASSPORT("护照"),
    /**
     * 其他证件
     */
    OTHER("其他");

    private final String description;

    IdType(String description) {
        this.description = description;
    }
}