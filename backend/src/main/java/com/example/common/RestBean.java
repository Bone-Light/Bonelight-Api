package com.example.common;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import org.slf4j.MDC;

import java.util.Optional;


public record RestBean<T> (long id, int code, T data, String message) {
    public static <T> RestBean<T> success(T data){return new RestBean<>(requestId(), 200, data, "请求成功");}
    public static <T> RestBean<T> success() {return success(null);}
    public static <T> RestBean<T> failure(int code, String message){return new RestBean<>(requestId(), code, null, message);}
    public static <T> RestBean<T> forbidden(String message) {return failure(403, message);}
    public static <T> RestBean<T> unauthorized(String message) {return failure(401, message);}
    public static <T> RestBean<T> noPermission() {return new RestBean<>(requestId(),401, null, "权限不足,拒绝请求");}

    public static <T> RestBean<T> RestResponse(ErrorCode errorCode) {
        return new RestBean<>(requestId(), errorCode.getCode(), null, errorCode.getMessage());
    }

    public String asJsonString(){return JSONObject.toJSONString(this, JSONWriter.Feature.WriteNulls);}

    private static long requestId(){
        String requestId = Optional.ofNullable(MDC.get("reqId")).orElse("0");
        return Long.parseLong(requestId);
    }
}