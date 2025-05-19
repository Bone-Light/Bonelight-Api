package com.example.DAO.entity;


import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
@Data
@TableName("api_info")
@AllArgsConstructor
@NoArgsConstructor
public class ApiInfo {
    //调用记录ID
    @TableId(type = IdType.AUTO)
    private Integer id;
    //用户ID
    @TableField("userId")
    private Integer userId;
    //接口ID
    @TableField("apiId") 
    private Integer apiId;
    //调用次数
    @TableField("totInvoke") 
    private Long totInvoke;
    //调用状态
    @TableField("status") 
    private Integer status;
    //创建时间
    @TableField("createTime") 
    private LocalDateTime createTime;
    //更新时间
    @TableField("updateTime")
    private LocalDateTime updateTime;
    //逻辑删除
    @TableLogic
    private Integer isDelete;
}