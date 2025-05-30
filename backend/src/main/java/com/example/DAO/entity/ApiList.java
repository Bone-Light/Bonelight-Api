package com.example.DAO.entity;


import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Data;
@Data
@TableName("api_list")
@AllArgsConstructor
@NoArgsConstructor
public class ApiList {
    //API-ID
    @TableId(type = IdType.AUTO)
    Integer id;
    //接口名
    String apiName;
    //接口地址
    String url;
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
    @TableLogic
    //逻辑删除
    Integer isDelete;
}