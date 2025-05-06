package com.example.DAO.entity;


import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
@Data
@TableName("api_list")
@AllArgsConstructor
@NoArgsConstructor
public class ApiList {
    //API-ID
    private Integer id;
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
    //调用次数
    private Long totInvoke;
    //创建时间
    private LocalDateTime createTime;
    //更新时间
    private LocalDateTime updateTime;
    //逻辑删除
    private Integer isDelete;
}