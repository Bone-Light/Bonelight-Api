package org.example.filters;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.InvokeCheckDTO;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ApiFilterBase extends AbstractResponseHandler implements GatewayFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        InvokeCheckDTO checkDTO = getInvokeCheckDTO(exchange);


        return handleResponse(exchange, chain, this::LogicTask);
    }

    // todo 业务逻辑
    private void LogicTask(String responseData) {
        // 路由A专属业务逻辑（如积分扣除）
        log.info("执行路由A业务逻辑，响应内容: {}", responseData);
    }

    @Override
    public int getOrder() {
        return 1; // 高于全局过滤器
    }

    private InvokeCheckDTO getInvokeCheckDTO(ServerWebExchange exchange){
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        return new InvokeCheckDTO()
                .setSign(headers.getFirst("accessKey"))
                .setNonce(headers.getFirst("nonce"))
                .setTimestamp(headers.getFirst("timestamp"))
                .setSign(headers.getFirst("sign"))
                .setBody(headers.getFirst("body"));
    }
}