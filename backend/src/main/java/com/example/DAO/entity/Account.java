package com.example.DAO.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableId(type = IdType.AUTO)
    Long id;
    //用户名
    String userName;
    //头像地址
    String userAvatar;
    //邮箱
    String email;
    //性别 0-男 1-女
    Integer sex;
    //用户角色 user/admin
    String userRole;
    //密码
    String password;
    //accessKey
    String accessKey;
    //secretKey
    String secretKey;
    //余额
    Long money;
    //邀请码
    String invitationCode;
    //账号状态 0-正常 1-封号
    Integer status;
    //创建时间
    LocalDateTime createTime;
    //更新时间
    LocalDateTime updateTime;
    //逻辑删除
    Integer isDelete;
}