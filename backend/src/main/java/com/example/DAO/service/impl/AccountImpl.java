package com.example.DAO.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.DAO.dto.AccountRegisterDTO;
import com.example.DAO.dto.AskCodeDTO;
import com.example.DAO.entity.Account;
import com.example.DAO.service.AccountService;
import com.example.DAO.mapper.AccountMapper;
import com.example.constant.Const;
import com.example.exception.BusinessException;
import com.example.utils.FlowUtil;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
* @author 吾骨封灯
* @since 2025-05-06 19:40:48
*/
@Service
public class AccountImpl extends ServiceImpl<AccountMapper, Account>
    implements AccountService{
    @Value("${spring.web.verify.mailLimit}")
    int busyTime;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private FlowUtil flowUtil;
    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void getCode(AskCodeDTO askCodeDTO, String ip) {
        ip = ip.replace(':','-');

        synchronized (ip.intern()) {
            if(keyBusy(ip)) throw new BusinessException(429, "验证码请求频繁");
            Random random = new Random();
            int code = random.nextInt(899999) + 100000;
            Map<String, Object> data = Map.of(
                    "type", askCodeDTO.getType(),
                    "email", askCodeDTO.getEmail(),
                    "code", code);
            rabbitTemplate.convertAndSend(Const.MQ_MAIL, data);
            stringRedisTemplate.opsForValue().set(Const.VERIFY_EMAIL_DATA + askCodeDTO.getEmail(), String.valueOf(code));
        }
    }

    @Override
    public Account findAccountByNameOrEmail(String username) {
        return this.query()
                .eq("username", username) .or() .eq("email", username)
                .one();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = this.findAccountByNameOrEmail(username);
        if(account == null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return User
                .withUsername(username)
                .password(account.getUserPassword())
                .roles(account.getUserRole())
                .build();
    }

    @Override
    public void registerByEmail(AccountRegisterDTO accountRegisterDTO) {
        String code = accountRegisterDTO.getCode();
        String email = accountRegisterDTO.getEmail();
        String name = accountRegisterDTO.getName();
        if(code == null) throw new BusinessException(400, "请先获取验证码");
        if(!code.equals(getEmailCode(email))) throw new BusinessException(400, "验证码错误，请重新输入");
        if(existAccountByEmail(email)) throw new BusinessException(400, "该邮箱已经被注册");
        if(existAccountByName(name)) throw new BusinessException(400, "该用户名已经使用");

        stringRedisTemplate.delete(Const.VERIFY_EMAIL_DATA + email);
        Account account = new Account();
        BeanUtils.copyProperties(accountRegisterDTO, account);
        account.setUserPassword(bCryptPasswordEncoder.encode(account.getUserPassword()));
        account.setAccessKey(bCryptPasswordEncoder.encode(name));
        account.setSecretKey(bCryptPasswordEncoder.encode(email));
        account.setCreateTime(LocalDateTime.now());
        account.setUpdateTime(LocalDateTime.now());
        this.save(account);
    }

    private boolean keyBusy(String key) {
        key = Const.VERIFY_EMAIL_LIMIT + key;
        return flowUtil.limitOnceCheck(key, busyTime);
    }

    private String getEmailCode(String email) {
        String key = Const.VERIFY_EMAIL_DATA + email;
        return stringRedisTemplate.opsForValue().get(key);
    }

    private boolean existAccountByEmail(String email) {
        return this.query().eq("email", email).count() > 0;
    }

    private boolean existAccountByName(String name) {
        return this.query().eq("userName", name).count() > 0;
    }
}




