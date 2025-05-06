package com.example.exception;
// todo **

import ch.qos.logback.core.spi.ErrorCodes;
import com.example.common.RestBean;
import jakarta.xml.bind.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


// todo: ErrorCode
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ValidationException.class)
    public RestBean<Void> validateExceptionHandler(ValidationException exception){
        log.warn("validateException [{}:{}]", exception.getClass().getName(), exception.getMessage());
        return RestBean.failure(400, "请求参数有误");
    }

    @ExceptionHandler(BusinessException.class)
    public RestBean<Void> businessExceptionHandler(BusinessException e){
        log.warn("businessException [{}:{}]", e.getCode(), e.getMessage());
        return RestBean.failure(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public RestBean<Void> runtimeExceptionHandler(RuntimeException e){
        log.warn("runtimeException [{}:{}]", e.getClass().getName(), e.getMessage());
        return RestBean.failure(ErrorCode.SYSTEM_ERROR, e.getMessage());
    }
}
