package com.example.DAO.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.DAO.dto.AccountDTOs.*;
import com.example.DAO.dto.PageDTO;
import com.example.DAO.entity.Account;
import com.example.DAO.service.AccountService;
import com.example.DAO.mapper.AccountMapper;
import com.example.constant.Const;
import com.example.exception.BusinessException;
import com.example.utils.FlowUtil;
import com.example.utils.KeyUtil;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
    @Resource
    private KeyUtil keyUtil;

    /*获取验证码*/
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
            stringRedisTemplate.opsForValue().set(
                    Const.VERIFY_EMAIL_DATA +
                            askCodeDTO.getType() + ":" +
                            askCodeDTO.getEmail(), String.valueOf(code),
                    300, TimeUnit.SECONDS);
        }
    }
    /*获取用户 - by email/name */
    @Override
    public Account findAccountByNameOrEmail(String username) {
        return this.query()
                .eq("username", username) .or() .eq("email", username)
                .one();
    }
    /*重写 security 验证用户*/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = this.findAccountByNameOrEmail(username);
        if(account == null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return User
                .withUsername(username)
                .password(account.getPassword())
                .roles(account.getUserRole())
                .build();
    }
    /*邮箱注册*/
    @Override
    public void registerByEmail(AccountRegisterDTO accountRegisterDTO) {
        String code = accountRegisterDTO.getCode();
        String email = accountRegisterDTO.getEmail();
        if(code == null) throw new BusinessException(400, "请先获取验证码");
        if(!code.equals(getCodeFromRedis(email, "register"))) throw new BusinessException(400, "验证码错误，请重新输入");
        stringRedisTemplate.delete(Const.VERIFY_EMAIL_DATA + "register:" + email);

        this.save(setAccount(accountRegisterDTO));
    }
    /*重置密码*/
    @Override
    public void resetPassword(AccountResetPwdDTO accountResetPwdDTO) {
        String code = accountResetPwdDTO.getCode();
        String email = accountResetPwdDTO.getEmail();
        String password = bCryptPasswordEncoder.encode(accountResetPwdDTO.getPassword());

        if(code == null) throw new BusinessException(400, "请先获取验证码");
        if(!code.equals(getCodeFromRedis(email, "reset"))) throw new BusinessException(400, "验证码错误，请重新输入");
        if(!existAccountByEmail(email)) throw new BusinessException(400, "该用户不存在");
        stringRedisTemplate.delete(Const.VERIFY_EMAIL_DATA + "reset:" + email);

        this.update()
                .set("password", password)
                .eq("email", email)
                .update();
    }
    /*获取用户 - by email */
    @Override
    public Account getAccountByEmail(String email) {
        return this.query().eq("email", email).one();
    }

    /** 用户注销 - 自己*/
    @Override
    public void deleteAccountBySelf(DeleteAccountBySelfDTO deleteAccountBySelfDTO){
        String code = deleteAccountBySelfDTO.getCode();
        String email = deleteAccountBySelfDTO.getEmail();
        if(code == null) throw new BusinessException(400, "请先获取验证码");
        if(!code.equals(getCodeFromRedis(email, "delete"))) throw new BusinessException(400, "验证码错误，请重新输入");
        stringRedisTemplate.delete(Const.VERIFY_EMAIL_DATA + "delete:" + email);
        this.remove(this.query().eq("email", email));
    }

    /** 用户注销 - 管理*/
    @PreAuthorize("hasRole='Admin'")
    @Override
    public void deleteAccountByAdmin(DeleteAccountByAdminDTO deleteAccountByAdminDTO){
        Long id = deleteAccountByAdminDTO.getId();
        this.remove(this.query().eq("id", id));
    }

    /** 添加用户 - 管理*/
    @PreAuthorize("hasRole='Admin'")
    @Override
    public void createAccountByAdmin(CreateAccountByAdminDTO createAccountByAdminDTO){
        this.save(setAccount(createAccountByAdminDTO));
    }

    /** 封禁用户 - 管理*/
    @PreAuthorize("hasRole='Admin'")
    @Override
    public void banAccountByAdmin(BanAccountByAdminDTO banAccountByAdminDTO){
        Long id = banAccountByAdminDTO.getId();
        this.update().eq("id", id).set("status", 1).update();
    }

    /** 解封用户 - 管理*/
    @PreAuthorize("hasRole('Admin')")
    @Override
    public void unLockAccountListPage(UnLockAccountListPageDTO unLockAccountListPageDTO){
        Long id = unLockAccountListPageDTO.getId();
        this.update().eq("id", id).set("status", 0).update();
    }

    /** 更新用户信息*/
    @Override
    public void updateAccountInfo(UpdateAccountInfoDTO updateAccountInfoDTO){
        Long id = updateAccountInfoDTO.getId();
        if(this.query().eq("id", id).count() == 0)  throw new BusinessException(401, "用户不存在");

        Account account = new Account();
        BeanUtils.copyProperties(updateAccountInfoDTO, account);
        account.setUpdateTime(LocalDateTime.now());
        this.updateById(account);
    }

    /** 获取用户列表*/
    @PreAuthorize("hasRole('Admin')")
    @Override
    public List<Account> getAccountList(){
        return this.list();
    }

    /** 获取用户列表页*/
    @Override
    public IPage<Account> getAccountListPage(PageDTO pageDTO){
        Page<Account> page = new Page<>(pageDTO.getCurrentPage(), pageDTO.getSize());
        return this.query().page(page);
    }

    /** 用户 Key 更新*/
    @Override
    public void updateAccountKey(UpdateAccountKeyDTO updateAccountKeyDTO){
        String ak = keyUtil.generateAK(updateAccountKeyDTO.getEmail());
        String sk = keyUtil.generateSK(ak);
        this.update().eq("id", updateAccountKeyDTO.getId()).set("accessKey", ak).set("secretKey", sk).update();
    }

    private boolean keyBusy(String key) {
        key = Const.VERIFY_EMAIL_LIMIT + key;
        return !flowUtil.limitOnceCheck(key, busyTime);
    }

    private String getCodeFromRedis(String email, String type) {
        String key = Const.VERIFY_EMAIL_DATA + type + ":" + email;
        return stringRedisTemplate.opsForValue().get(key);
    }

    private Account setAccount(Object obj) {
        Account account = new Account();
        BeanUtils.copyProperties(obj, account);
        String email = account.getEmail();
        String name = account.getUsername();
        if(existAccountByEmail(email)) throw new BusinessException(400, "该邮箱已经被注册");
        if(existAccountByName(name)) throw new BusinessException(400, "该用户名已经使用");
        String password = account.getPassword();

        String ak = keyUtil.generateAK(email);
        String sk = keyUtil.generateSK(ak);
        account.setPassword(bCryptPasswordEncoder.encode(password));
        account.setAccessKey(ak);
        account.setSecretKey(sk);
        account.setCreateTime(LocalDateTime.now());
        account.setUpdateTime(LocalDateTime.now());
        return account;
    }

    private boolean existAccountByEmail(String email) {
        return this.query().eq("email", email).count() > 0;
    }

    private boolean existAccountByName(String name) {
        return this.query().eq("username", name).count() > 0;
    }


}




