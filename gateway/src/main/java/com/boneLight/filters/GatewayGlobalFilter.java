package com.boneLight.filters;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


// todo
@Slf4j
@Component
public class GatewayGlobalFilter implements GlobalFilter, Ordered {
    // todo 注册中心， common
//    @DubboReference
//    private DubboUserService userService;

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

    // 定义响应式处理方法，返回 Mono<Void> 表示异步无返回值的处理流程
    public Mono<Void> handleResponse(ServerWebExchange exchange,
                                     GatewayFilterChain chain,
                                     int param1,
                                     int param2) {
        // 获取原始响应对象（核心组件，包含响应状态/头信息/响应体等）
        ServerHttpResponse originalResponse = exchange.getResponse();
        // 获取 DataBuffer 工厂，用于创建/包装数据缓冲区（处理二进制流的核心类）
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
                        // todo 额外的处理逻辑 G
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
        return 0; // 执行顺序
    }
}