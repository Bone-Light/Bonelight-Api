package com.example.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class GatewayHeaderFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws IOException, ServletException {
        // 校验请求头
        if (request.getHeader("Light-Gateway-Request") == null &&
                !request.getHeader("Light-Gateway-Request").equals("true")) {

            log.info("未经网关的非法请求 IP:{}", request.getRemoteAddr());
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "非法请求来源");
            return; // 终止请求
        }
        chain.doFilter(request, response); // 继续处理
    }
}