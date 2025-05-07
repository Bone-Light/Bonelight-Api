package com.example.DAO.service;

import com.example.DAO.dto.AccountDTOs.AccountRegisterDTO;
import com.example.DAO.dto.AccountDTOs.AccountResetPwdDTO;
import com.example.DAO.dto.AccountDTOs.AskCodeDTO;
import com.example.DAO.entity.Account;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
* @author 吾骨封灯
* @since 2025-05-06 19:40:48
*/
public interface AccountService extends IService<Account>, UserDetailsService {
    Account findAccountByNameOrEmail(String username);
    void getCode(AskCodeDTO askCodeDTO, String ip);
    void registerByEmail(AccountRegisterDTO accountRegisterDTO);
    void resetPassword(AccountResetPwdDTO accountResetPwdDTO);
}
