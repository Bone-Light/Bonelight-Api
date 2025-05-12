package org.example.filters;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


// todo
@Slf4j
@Component
public class GatewayGlobalFilter implements GlobalFilter, Ordered {
    // todo 注册中心， common
    @DubboReference
    private DubboUserservice userservice;

    private static final List<String> IP_WHITE_LIST = List.of("127.0.0.1");


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1 日志
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();
        String method = request.getMethod().toString();

        log.info("请求唯一标识：{}", request.getId());
        log.info("请求路径：{}", path);
        log.info("请求方法：{}", method);
        log.info("请求参数：{}", request.getQueryParams());
        // 获取网关本地地址
        String localAddress = Objects.requireNonNull(request.getLocalAddress()).getHostString();
        log.info("网关接收地址: {}", localAddress);
        // 获取客户端地址（可能为代理IP）
        String remoteAddress = Objects.requireNonNull(request.getRemoteAddress()).getHostString();
        log.info("直接连接地址: {}", remoteAddress);
        // 获取真实客户端IP（需配置代理支持 -- 反向代理时处理好才可以）
        String realClientIp = request.getHeaders().getFirst("X-Forwarded-For");
        log.info("真实客户端IP: {}", realClientIp);

        ServerHttpResponse response = exchange.getResponse();
        if(!IP_WHITE_LIST.contains(remoteAddress)){ // todo 代理 / 真实 ??
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }
        return handleResponse(exchange, chain, 1, 1);
//        return chain.filter(exchange);
    }

    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, long interfaceInfoId, long userId) {

    }

    @Override
    public int getOrder() {
        return 0; // 执行顺序
    }
}