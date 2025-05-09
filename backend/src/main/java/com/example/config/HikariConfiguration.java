package com.example.config;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

@Component
public class HikariConfiguration {
    @Resource
    private DataSource dataSource;

    @EventListener(ApplicationReadyEvent.class)
    public void init() throws SQLException {
        dataSource.getConnection().close();  // 强制建立首个连接
    }
}