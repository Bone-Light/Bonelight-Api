package com.example.DAO.service;

import com.example.DAO.dto.AskCodeDTO;
import com.example.DAO.entity.Account;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
* @author 吾骨封灯
* @since 2025-05-06 19:40:48
*/
public interface AccountService extends IService<Account>, UserDetailsService {
    Account findAccountByNameOrEmail(String username);
    String getCode(AskCodeDTO askCodeDTO, String ip);
}
