package com.example.DAO.vo.ApiListVOs;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetApiInfoVO {
    //API-ID
    Integer id;
    //接口名
    String apiName;
    //接口头像
    String avatar;
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
    //调用次数
    Long totInvoke;
    //创建时间
    LocalDateTime createTime;
    //更新时间
    LocalDateTime updateTime;
}
