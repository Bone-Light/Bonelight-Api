package com.boneLight.utils;

import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SignUtil {
    @Resource
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    public String genSign(String body, String secretKey){
        String content = body + "." + secretKey;
        return bCryptPasswordEncoder.encode(content);
    }
}
