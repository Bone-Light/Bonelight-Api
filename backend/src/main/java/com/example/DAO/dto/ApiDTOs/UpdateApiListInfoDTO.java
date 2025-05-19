package com.example.DAO.dto.ApiDTOs;

import lombok.Data;

@Data
public class UpdateApiListInfoDTO {
    //接口 id
    Integer id;
    //接口名
    String apiName;
    //接口地址
    String url;
    //创建用户ID
    Long createUserId;
    //接口类型
    String method;
    //请求体
    String requestParams;
    //响应体
    String responseParams;
    //费用
    Long cost;
    //请求案例
    String requestExample;
    //请求头
    String requestHeader;
    //响应格式
    String responseFormat;
    //描述
    String description;
    //状态 0-下线 1-上线
    Integer status;
}
