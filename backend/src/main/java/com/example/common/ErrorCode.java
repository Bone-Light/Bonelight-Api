package com.example.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum ErrorCode {
    SUCCESS(200, "Success"),
    PARAMETER_ERROR(401, "请求参数错误--理论上不在这里出现"),
    SYSTEM_ERROR(500, "系统内部错误"),

    NOT_LOGIN(40101, "用户未登录"),
    NOT_PERMISSION(40300, "用户无权限"),
    Forbidden_User(40301, "当前用户已封禁"),
    Forbidden_Get(40302, "禁止访问"),
    NOT_FOUND(40400, "请求数据不存在");

    private final int code;
    private final String message;
}
