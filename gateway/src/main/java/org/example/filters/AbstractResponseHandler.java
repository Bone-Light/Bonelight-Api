package org.example.filters;

import lombok.NonNull;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public abstract class AbstractResponseHandler {
    // 模板方法封装通用流程
    protected Mono<Void> handleResponse(ServerWebExchange exchange,
                                        GatewayFilterChain chain,
                                        Consumer<String> businessLogic) {
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(exchange.getResponse()) {
            @Override
            @NonNull
            public Mono<Void> writeWith(@NonNull Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux<? extends DataBuffer> fluxBody) {
                    return super.writeWith(fluxBody.map(buffer -> {
                        String responseData = extractResponseData(buffer);
                        // 执行子类业务逻辑
                        businessLogic.accept(responseData);
                        return buffer;
                    }));
                }
                return super.writeWith(body);
            }
        };
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }

    private String extractResponseData(DataBuffer buffer) {
        byte[] content = new byte[buffer.readableByteCount()];
        buffer.read(content);
        DataBufferUtils.release(buffer);
        return new String(content, StandardCharsets.UTF_8);
    }
}