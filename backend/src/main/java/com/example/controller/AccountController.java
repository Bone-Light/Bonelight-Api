package com.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.DAO.dto.AccountDTOs.*;
import com.example.DAO.dto.PageDTO;
import com.example.DAO.entity.Account;
import com.example.DAO.service.AccountService;
import com.example.common.RestBean;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/** 用户接口 */
@Validated
@RestController
@RequestMapping("/api/auth")
public class AccountController {
    @Resource
    private AccountService accountService;

    /** 用户注册 */
    @PostMapping("/register")
    public RestBean<Void> registerAccount(@RequestBody @Valid @NotNull AccountRegisterDTO accountRegisterDTO) {
        accountService.registerByEmail(accountRegisterDTO);
        return RestBean.success();
    }

    /** 获取验证码 */
    @GetMapping("/get-code")
    public RestBean<String> getCode(@Valid AskCodeDTO askCodeDTO, HttpServletRequest request) {
        accountService.getCode(askCodeDTO,request.getLocalAddr());
        return RestBean.success();
    }

    /** 重置密码 */
    @PostMapping("/reset-password")
    public RestBean<Void> resetPassword(@RequestBody @NotNull AccountResetPwdDTO accountResetPwdDTO) {
        accountService.resetPassword(accountResetPwdDTO);
        return RestBean.success();
    }

    /** 用户注销 - 自己*/
    @PostMapping("deleteAccountBySelf")
    public RestBean<Void> deleteAccountBySelf(DeleteAccountBySelfDTO deleteAccountBySelfDTO){
        this.accountService.deleteAccountBySelf(deleteAccountBySelfDTO);
        return RestBean.success();
    }

    /** 用户注销 - 管理*/
    @PostMapping("deleteAccountByAdmin")
    public RestBean<Void> deleteAccountByAdmin(DeleteAccountByAdminDTO deleteAccountByAdminDTO){
        this.accountService.deleteAccountByAdmin(deleteAccountByAdminDTO);
        return RestBean.success();
    }

    /** 添加用户 - 管理*/
    @PostMapping("createAccountByAdmin")
    public RestBean<Void> createAccountByAdmin(CreateAccountByAdminDTO createAccountByAdminDTO){
        accountService.createAccountByAdmin(createAccountByAdminDTO);
        return RestBean.success();
    }

    /** 封禁用户 - 管理*/
    @PostMapping("banAccountByAdmin")
    public RestBean<Void> banAccountByAdmin(BanAccountByAdminDTO banAccountByAdminDTO){
        this.accountService.banAccountByAdmin(banAccountByAdminDTO);
        return RestBean.success();
    }

    /** 解封用户 - 管理*/
    @PostMapping("unLockAccountListPage")
    public RestBean<Void> unLockAccountListPage(UnLockAccountListPageDTO unLockAccountListPageDTO){
        this.accountService.unLockAccountListPage(unLockAccountListPageDTO);
        return RestBean.success();
    }

    /** 更新用户信息*/
    @PostMapping("updateAccountInfo")
    public RestBean<Void> updateAccountInfo(UpdateAccountInfoDTO updateAccountInfoDTO){
        this.accountService.updateAccountInfo(updateAccountInfoDTO);
        return RestBean.success();
    }

    /** 获取用户列表 - 管理*/
    @GetMapping("getAccountList")
    public RestBean<List<Account>> getAccountList(){
        return RestBean.success(accountService.getAccountList());
    }

    /** 获取用户列表页 - 管理*/
    @GetMapping("getAccountListPage")
    public RestBean<IPage<Account>> getAccountListPage(PageDTO pageDTO){
        return RestBean.success(accountService.getAccountListPage(pageDTO));
    }

    /** 用户 Key 更新*/
    @PostMapping("updateAccountKey")
    public RestBean<Void> updateAccountKey(UpdateAccountKeyDTO updateAccountKeyDTO){
        this.accountService.updateAccountKey(updateAccountKeyDTO);
        return RestBean.success();
    }
}
