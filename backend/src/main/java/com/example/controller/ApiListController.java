package com.example.controller;

import com.example.DAO.entity.ApiList;
import com.example.common.RestBean;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/** apiList 接口 */
@Validated
@RestController
@RequestMapping("/apiList")
public class ApiListController {
    /** 获取Api列表 */
    @GetMapping("list")
    public RestBean<ApiList> getApiList() {
        return RestBean.success(new ApiList());
    }
}