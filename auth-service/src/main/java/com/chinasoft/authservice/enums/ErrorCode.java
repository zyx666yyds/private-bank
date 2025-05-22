package com.chinasoft.authservice.enums;


import lombok.Getter;

@Getter
public enum ErrorCode {

    SUCCESS(200, "success", "请求成功"),
    BAD_REQUEST(400, "bad request", "参数错误或校验失败"),
    UNAUTHORIZED(401, "unauthorized", "未登录或token失效"),
    FORBIDDEN(403, "forbidden", "权限不足"),  // 修正表格中403的错误描述
    NOT_FOUND(404, "not found", "资源不存在"),
    INTERNAL_ERROR(500, "internal server error", "后端异常");

    private final int code;
    private final String message;
    private final String scene;

    ErrorCode(int code, String message, String scene) {
        this.code = code;
        this.message = message;
        this.scene = scene;
    }

    // 通过code获取枚举对象（含容错逻辑）
    public static ErrorCode fromCode(int code) {
        for (ErrorCode errorCode : values()) {
            if (errorCode.code == code) {
                return errorCode;
            }
        }
        return INTERNAL_ERROR; // 默认返回500
    }
}