package com.example.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfiguration {
    // 接口分组配置
    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("admin-api")
                .pathsToMatch("/admin/**")  // 匹配接口路径
                .packagesToScan("com.example.admin")  // 扫描包路径
                .build();
    }

    // 文档元数据配置
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API文档")
                        .version("1.0")
                        .description("这是系统的核心API文档，包含用户管理、Api列表等接口...")
                        .contact(new Contact() // 联系方式
                                .name("技术支持")
                                .email("AveMujica@bangbang.com"))
                        .license(new License() // 许可证
                                .name("没有许可证捏-不给用")
                                .url("http://springdoc.org")));
/*
                        .servers(List.of(
                            new Server().url("http://localhost:8080").description("开发环境"),
                            new Server().url("https://api.example.com").description("生产环境")
                        ))
*/
    }
}