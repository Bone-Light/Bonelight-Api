package com.example.utils;

import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.UUID;

@Component
public class KeyUtil {
    @Resource
    private BCryptPasswordEncoder bCryptEncoder;
    private static final int AK_RANDOM_LENGTH = 16;

    // 生成AccessKey（格式：AK_用户ID_时间戳_随机码）
    public String generateAK(String userId) {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String random = generateSecureRandom(AK_RANDOM_LENGTH);
        return String.format("AK_%s_%s_%s", userId, timestamp, random);
    }

    // 生成SecretKey（动态盐值+BCrypt强哈希）
    public String generateSK(String ak) {
        // 动态盐值包含AK+时间戳+UUID
        String dynamicSalt = ak + System.currentTimeMillis()
                + UUID.randomUUID().toString().replace("-", "");
        return bCryptEncoder.encode(dynamicSalt);
    }

    // 安全随机数生成
    private String generateSecureRandom(int length) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
