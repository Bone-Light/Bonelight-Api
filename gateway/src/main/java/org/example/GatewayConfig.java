package org.example;

import org.example.filters.BackendFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("api-service", r -> r.path("/apiList/invoke/**") // todo 调用接口url
                        .filters(f -> f
                                .filter(new BackendFilter())
                                .rewritePath("/apiList/(?<id>.*)", "/${id}"))
                        .uri("lb://order-service"))
                .route("backend-service", r -> r.path("/**")
                        .uri("http://localhost:8081")) // todo 服务器地址
                .build();
    }
}