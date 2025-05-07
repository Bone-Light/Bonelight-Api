package com.example.DAO.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.DAO.dto.AskCodeDTO;
import com.example.DAO.entity.Account;
import com.example.DAO.service.AccountService;
import com.example.DAO.mapper.AccountMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
* @author 吾骨封灯
* @since 2025-05-06 19:40:48
*/
@Service
public class AccountImpl extends ServiceImpl<AccountMapper, Account>
    implements AccountService{
    @Override
    public String getCode(AskCodeDTO askCodeDTO, String ip) {

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
}




