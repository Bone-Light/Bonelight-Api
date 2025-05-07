package com.example.controller;

import com.example.DAO.dto.AccountRegisterDTO;
import com.example.DAO.dto.AskCodeDTO;
import com.example.DAO.entity.Account;
import com.example.DAO.service.AccountService;
import com.example.common.RestBean;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AccountController {
    @Resource
    private AccountService accountService;

    // todo
    @PostMapping("/register")
    public RestBean<Void> registerAccount(@RequestBody @Valid AccountRegisterDTO accountRegisterDTO) {
        accountService.registerByEmail(accountRegisterDTO);
        return RestBean.success();
    }

    @PostMapping("/ask-code")
    public RestBean<String> askCode(@RequestBody @Valid @NotNull AskCodeDTO askCodeDTO, HttpServletRequest request) {
        accountService.getCode(askCodeDTO,request.getLocalAddr());
        return RestBean.success();
    }

    // todo
    @PostMapping("/reset-password")
    public RestBean<Void> resetPassword(@RequestBody @NotNull XxxDto XxxDto) {

        return RestBean.success();
    }
}
