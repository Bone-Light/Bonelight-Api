package com.example.DAO.dto.ApiDTOs;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddApiDTO {
    //接口名
    private String apiName;
    //接口地址
    private String url;
    //创建用户ID
    private Long createUserId;
    //接口类型
    private String method;
    //请求体
    private String requestParams;
    //响应体
    private String responseParams;
    //费用
    private Long cost;
    //请求案例
    private String requestExample;
    //请求头
    private String requestHeader;
    //响应格式
    private String responseFormat;
    //描述
    private String description;
    //状态 0-下线 1-上线
    private Integer status;
}
