package com.boneLight.filters;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class ApiFilter implements GatewayFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 3. 用户鉴权（判断 ak、sk 是否合法）
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        String body = headers.getFirst("body");

        return handleResponse(exchange, chain);
    }

    // 定义响应式处理方法，返回 Mono<Void> 表示异步无返回值的处理流程
    public Mono<Void> handleResponse(ServerWebExchange exchange,
                                     GatewayFilterChain chain) {
        // 获取原始响应对象（核心组件，包含响应状态/头信息/响应体等）[3,9](@ref)
        ServerHttpResponse originalResponse = exchange.getResponse();
        // 获取 DataBuffer 工厂，用于创建/包装数据缓冲区（处理二进制流的核心类）[12,13](@ref)
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();

        HttpStatusCode status =  originalResponse.getStatusCode();
        if(status != HttpStatus.OK){return chain.filter(exchange);}

        // 创建响应装饰器（关键设计模式，用于拦截响应写入过程
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
            @Override
            @NonNull
            // 重写 writeWith 方法（响应式数据写入的入口
            public Mono<Void> writeWith(@NonNull Publisher<? extends DataBuffer> body) {
                // 判断是否为 Flux 类型（响应式流的核心发布者）
                // 将泛型 Publisher 转换为 Flux（确保流式处理）
                if (body instanceof Flux<? extends DataBuffer> fluxBody) {
                    // 对每个 DataBuffer 进行映射处理（响应式操作符）
                    return super.writeWith(fluxBody.map(
                            dataBuffer -> {
                                // todo 额外的处理逻辑 A
//                        // 扣除积分
//                        redissonLockUtil.redissonDistributedLocks(("gateway_" + user.getUserAccount()).intern(), () -> {
//                            boolean invoke = interfaceInvokeService.invoke(interfaceInfo.getId(), user.getId(), interfaceInfo.getReduceScore());
//                            if (!invoke) {
//                                throw new BusinessException(ErrorCode.OPERATION_ERROR, "接口调用失败");
//                            }
//                        }, "接口调用失败");

                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                // 释放掉内存
                                DataBufferUtils.release(dataBuffer);
                                String data = new String(content, StandardCharsets.UTF_8);
                                // 打印日志
                                log.info("响应内容: {}", data);
                                // 重新包装为新的 DataBuffer（避免原始缓冲区被释放）[13](@ref)
                                return bufferFactory.wrap(content);
                            }));
                }
                // 非 Flux 类型直接透传（如 Mono 类型）[5](@ref)
                return super.writeWith(body);
            }
        };
        // 构建新的 exchange 对象（通过 mutate 方法创建副本, 放入装饰类)
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }


    @Override
    public int getOrder() {
        return 10;
    }
}