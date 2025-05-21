package com.example.config;

import com.example.DAO.entity.Account;
import com.example.DAO.service.AccountService;
import com.example.DAO.vo.LoginVO;
import com.example.common.RestBean;
import com.example.constant.Const;
import com.example.filter.GatewayHeaderFilter;
import com.example.filter.RequestLogFilter;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class SecurityConfiguration {
    @Resource
    RequestLogFilter requestLogFilter;

    @Resource
    GatewayHeaderFilter gatewayHeaderFilter;

    @Resource
    AccountService accountService;

    @Bean
    public SecurityFilterChain securityWebFilterChain(
            HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(conf -> conf
                        .requestMatchers("/api/auth/**", "/error").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/doc.html", "/webjars/**").permitAll()
                        .anyRequest().hasAnyRole(Const.ROLE_ADMIN, Const.ROLE_NORMAL)
                )
                .formLogin(conf -> conf
                        .loginProcessingUrl("/api/auth/login")
                        .failureHandler(this::handleProcess)
                        .successHandler(this::handleProcess)
                        .permitAll()
                )
                .logout(conf ->conf
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessHandler(this::onLogoutSuccess)
                )
                .exceptionHandling(conf -> conf
                        .accessDeniedHandler(this::handleProcess)
                        .authenticationEntryPoint(this::handleProcess)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(conf -> conf
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 按需创建
                        .maximumSessions(1) // 最大会话数
                        .maxSessionsPreventsLogin(true)
                ) // session 逻辑
                .addFilterBefore(requestLogFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(gatewayHeaderFilter, RequestLogFilter.class)
                .build();
    }

    private void handleProcess(HttpServletRequest request, HttpServletResponse response, Object exceptionOrAuthentication) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        if(exceptionOrAuthentication instanceof AccessDeniedException exception){
            writer.write(RestBean
                    .forbidden(exception.getMessage()).asJsonString());
        } else if(exceptionOrAuthentication instanceof Exception exception){
            writer.write(RestBean
                    .unauthorized(exception.getMessage()).asJsonString());
        } else if(exceptionOrAuthentication instanceof Authentication authentication){
            User user = (User) authentication.getPrincipal();
            Account account = accountService.findAccountByNameOrEmail(user.getUsername());
            LoginVO vo = new LoginVO();
            BeanUtils.copyProperties(account, vo);
            writer.write(RestBean.success(vo).asJsonString());
        } // 密码校验是隐式的, 实现 BCryptPasswordEncoder 就好
    }

    private void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        request.getSession().invalidate();
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(RestBean.success("退出登录成功").asJsonString());
    }
}