package com.example.DAO.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.DAO.dto.AccountDTOs.*;
import com.example.DAO.dto.PageDTO;
import com.example.DAO.entity.Account;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.DAO.mapper.AccountMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
* @author 吾骨封灯
* @since 2025-05-06 19:40:48
*/
public interface AccountService extends IService<Account>, UserDetailsService {
    Account findAccountByNameOrEmail(String username);
    void getCode(AskCodeDTO askCodeDTO, String ip);
    Account getAccountByEmail(String email);
    void registerByEmail(AccountRegisterDTO accountRegisterDTO);
    void resetPassword(AccountResetPwdDTO accountResetPwdDTO);

    /** 用户注销 - 自己*/
    void deleteAccountBySelf(DeleteAccountBySelfDTO deleteAccountBySelfDTO);

    /** 用户注销 - 管理*/
    @PreAuthorize("hasRole='Admin'")
    void deleteAccountByAdmin(DeleteAccountByAdminDTO deleteAccountByAdminDTO);

    /** 添加用户 - 管理*/
    @PreAuthorize("hasRole='Admin'")
    void createAccountByAdmin(CreateAccountByAdminDTO createAccountByAdminDTO);

    /** 封禁用户 - 管理*/
    @PreAuthorize("hasRole='Admin'")
    void banAccountByAdmin(BanAccountByAdminDTO banAccountByAdminDTO);

    /** 解封用户 - 管理*/
    @PreAuthorize("hasRole('Admin')")
    void unLockAccountListPage(UnLockAccountListPageDTO unLockAccountListPageDTO);

    /** 更新用户信息*/
    void updateAccountInfo(UpdateAccountInfoDTO updateAccountInfoDTO);

    /** 获取用户列表*/
    @PreAuthorize("hasRole('Admin')")
    List<Account> getAccountList();

    /** 获取用户列表页*/
    IPage<Account> getAccountListPage(PageDTO pageDTO);

    /** 用户 Key 更新*/
    void updateAccountKey(UpdateAccountKeyDTO updateAccountKeyDTO);
}
