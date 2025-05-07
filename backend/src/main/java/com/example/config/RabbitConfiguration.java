package com.example.config;

import com.example.constant.Const;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;


public class RabbitConfiguration {
    @Bean
    public Queue mailQueue() {
        return new Queue(Const.MQ_MAIL, true); // true表示持久化
    }
}
