package com.example.listener;

import com.example.DAO.service.AccountService;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;

@Component
@RabbitListener(queues = "mail")
public class MailQueueListener {
    @Resource
    JavaMailSender mailSender;
    @Resource
    TemplateEngine templateEngine;
    @Resource
    AccountService accountService;
    @Value("${spring.mail.username}")
    String username;

    @RabbitHandler
    public void sendMailMessage(Map<String, Object> data) throws MessagingException {
        String email = data.get("email").toString();
        Integer code = (Integer) data.get("code");
        String type = data.get("type").toString();
        Map<String, Object> valueMap = new HashMap<>(Map.of("username", email, "code", code));

        switch (type) {
            case "register" -> sendHtmlEmail(email,"您的注册验证码邮件",
                    "code-email",valueMap);
            case "modify" -> {
                valueMap.put("username", accountService.getAccountByEmail(email).getUserName());
                sendHtmlEmail(email,"您的修改密码邮件",
                "code-email",valueMap);
            }
        }
    }

    private void sendHtmlEmail(String to, String subject, String templateName,
                              Map<String, Object> valueMap) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        // 使用Thymeleaf渲染HTML内容
        Context context = new Context();
        context.setVariables(valueMap);
        String htmlContent = templateEngine.process(templateName, context);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        helper.setFrom(username);
        mailSender.send(message);
    }
}