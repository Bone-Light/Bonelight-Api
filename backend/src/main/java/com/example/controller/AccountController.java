package com.example.controller;

import com.example.DAO.dto.AccountDTOs.AccountRegisterDTO;
import com.example.DAO.dto.AccountDTOs.AccountResetPwdDTO;
import com.example.DAO.dto.AccountDTOs.AskCodeDTO;
import com.example.DAO.service.AccountService;
import com.example.common.RestBean;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AccountController {
    @Resource
    private AccountService accountService;

    @PostMapping("/register")
    public RestBean<Void> registerAccount(@RequestBody @Valid AccountRegisterDTO accountRegisterDTO) {
        accountService.registerByEmail(accountRegisterDTO);
        return RestBean.success();
    }

    @PostMapping("/get-code")
    public RestBean<String> getCode(@RequestBody @Valid @NotNull AskCodeDTO askCodeDTO, HttpServletRequest request) {
        accountService.getCode(askCodeDTO,request.getLocalAddr());
        return RestBean.success();
    }

    // todo
    @PostMapping("/reset-password")
    public RestBean<Void> resetPassword(@RequestBody @NotNull AccountResetPwdDTO accountResetPwdDTO) {
        accountService.resetPassword(accountResetPwdDTO);
        return RestBean.success();
    }
}
