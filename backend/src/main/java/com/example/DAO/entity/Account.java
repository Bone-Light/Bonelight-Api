package com.example.DAO.entity;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
@Data
@TableName("account")
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    //Account-ID
    private Long id;
    //用户名
    private String userName;
    //头像地址
    private String userAvatar;
    //邮箱
    private String email;
    //性别 0-男 1-女
    private Integer sex;
    //用户角色 user/admin
    private String userRole;
    //密码
    private String userPassword;
    //accessKey
    private String accessKey;
    //secretKey
    private String secretKey;
    //余额
    private Long money;
    //邀请码
    private String invitationCode;
    //账号状态 0-正常 1-封号
    private Integer status;
    //创建时间
    private LocalDateTime createTime;
    //更新时间
    private LocalDateTime updateTime;
    //逻辑删除
    private Integer isDelete;
}