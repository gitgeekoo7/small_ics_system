package com.cesecsh.small_ics_system.util;

public enum ResponseCode {
    ERROR("-1", "系统繁忙,请稍候再试."),
    SUCCESS("0", "请求成功.");

    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
