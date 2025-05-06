package com.example.DAO.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.DAO.entity.Account;
import com.example.DAO.service.AccountService;
import com.example.DAO.mapper.AccountMapper;
import org.springframework.stereotype.Service;

/**
* @author 吾骨封灯
* @since 2025-05-06 19:40:48
*/
@Service
public class AccountImpl extends ServiceImpl<AccountMapper, Account>
    implements AccountService{

    @Override
    public Account findAccountByNameOrEmail(String username) {
        return this.query()
                .eq("username", username) .or() .eq("email", username)
                .one();
    }
}




