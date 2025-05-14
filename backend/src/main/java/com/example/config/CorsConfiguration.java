package com.example.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// todo 删除跨域配置 -- 网关实现了
@Slf4j
@Configuration
public class CorsConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 所有路径生效
//                .allowedOrigins("*") // 当allowCredentials=true时，allowedOrigins不能使用通配符"*"
                .allowedOriginPatterns("*")  // 关键修改点
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)  // 允许携带凭证（如 Cookie）
                .exposedHeaders("*") // 允许访问所以自定义字段
                .maxAge(3600);  // 预检请求缓存时间（秒）
    }
}
