package com.cesecsh.small_ics_system.util;

import lombok.Getter;

@Getter
public class Result {
    private String code;//状态码
    private String msg;//提示信息
    private Object data;

    public Result(String code, String msg) {
        this(code, msg, null);
    }

    public Result(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result success() {
        return success(ResponseCode.SUCCESS.getMessage(), null);
    }

    public static Result success(String msg) {
        return success(msg, null);
    }

    public static Result success(Object object) {
        return success(ResponseCode.SUCCESS.getMessage(), object);
    }

    public static Result success(String msg, Object object) {
        return new Result(ResponseCode.SUCCESS.getCode(), msg, object);
    }

    public static Result error() {
        return error(ResponseCode.ERROR.getMessage());
    }

    public static Result error(String msg) {
        return new Result(ResponseCode.ERROR.getCode(), msg);
    }
}
