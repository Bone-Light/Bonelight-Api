package com.example.exception;

import com.example.common.RestBean;
import jakarta.xml.bind.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ValidationException.class)
    public RestBean<Void> validateExceptionHandler(ValidationException e){
        log.warn("validateException [{}:{}]", e.getClass().getName(), e.getMessage());
        return RestBean.failure(400, e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public RestBean<Void> businessExceptionHandler(BusinessException e){
        log.warn("businessException [{}:{}]", e.getCode(), e.getMessage());
        return RestBean.failure(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public RestBean<?> runtimeExceptionHandler(RuntimeException e){
        log.warn("runtimeException [{}:{}]", e.getClass().getName(), e.getMessage());
        return RestBean.failure(500, "服务器内部异常，请联系管理员");
    }
}
