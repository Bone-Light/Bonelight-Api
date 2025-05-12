package org.example.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class GatewayGlobalFilterBase extends AbstractResponseHandler implements GatewayFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return handleResponse(exchange, chain, this::LogicTask);
    }

    private void LogicTask(String responseData) {
        // 路由A专属业务逻辑（如积分扣除）
        log.info("[全局路由] 响应内容: {}", responseData);
    }

    @Override
    public int getOrder() {
        return 1; // 高于全局过滤器
    }
}