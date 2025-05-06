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
    NOT_LOGIN(2, "Not Login"),
    NOT_PERMISSION(3, "Not Authenticated"),

    ;

    private final int code;
    private final String message;
}
