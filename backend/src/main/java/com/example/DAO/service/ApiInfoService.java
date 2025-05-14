package com.example.DAO.service;

import com.example.DAO.dto.ApiInfoDTOs.InvokeDTO;
import com.example.DAO.entity.ApiInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 吾骨封灯
* @since 2025-05-14 09:48:41
*/
public interface ApiInfoService extends IService<ApiInfo> {
    public void invoke(InvokeDTO invokeDTO);
}
