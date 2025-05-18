package com.example.DAO.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.DAO.entity.ApiList;
import com.example.DAO.service.ApiListService;
import com.example.DAO.mapper.ApiListMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
* @author 吾骨封灯
* @since 2025-05-06 19:40:48
*/
@Service
public class ApiListImpl extends ServiceImpl<ApiListMapper, ApiList>
    implements ApiListService{
    /*todo 添加 Api 接口*/
    @Override
    @PreAuthorize("hasRole='Admin'")
    public void addApi(){

    }

    /*todo  删除 Api 接口*/
    @Override
    @PreAuthorize("hasRole='Admin'")
    public void addApi(){

    }

    /*todo 修改 Api 图标*/
    @Override
    @PreAuthorize("hasRole='Admin'")
    public void updateApiAvatar(){

    }

    /*todo 更改 Api 信息*/
    @Override
    @PreAuthorize("hasRole='Admin'")
    public void updateApiInfo(){

    }

    /*todo 获取 Api 信息*/
    @Override
    public void getApiInfo(){

    }

    /*todo 获取 Api 列表*/
    @Override
    public void getApiList(){

    }

    /*todo 获取 Api 列表页*/
    @Override
    public void getApiListPage(){

    }

    /*todo 搜索 Api */
    @Override
    public void getApiListPage(){

    }


}





