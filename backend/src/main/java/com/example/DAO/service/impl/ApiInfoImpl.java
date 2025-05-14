package com.example.DAO.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.DAO.dto.ApiInfoDTOs.InvokeDTO;
import com.example.DAO.entity.ApiInfo;
import com.example.DAO.service.ApiInfoService;
import com.example.DAO.mapper.ApiInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author 吾骨封灯
* @since 2025-05-14 09:48:41
*/
@Service
public class ApiInfoImpl extends ServiceImpl<ApiInfoMapper, ApiInfo>
    implements ApiInfoService{

    @Override
    public void invoke(InvokeDTO invokeDTO) {
        // 构造实体对象（用于插入时的初始值）
        ApiInfo entity = new ApiInfo();
        entity.setUserId(invokeDTO.getUserId());
        entity.setApiId(invokeDTO.getApiId());
        entity.setTotInvoke(0L); // 初始调用次数为0
        entity.setCreateTime();
        entity.setCreateTime();

        // 构建UpdateWrapper设置更新逻辑和条件
        UpdateWrapper<ApiInfo> wrapper = new UpdateWrapper<>();
        wrapper.eq("user_id", invokeDTO.getUserId())
                .eq("api_id", invokeDTO.getApiId())
                // 动态更新逻辑（存在记录时执行）
                .setSql("cost = cost - {0}", invokeDTO.getCost())
                .setSql("tot_invoke = tot_invoke + 1");

        // （存在则更新，否则插入）
        if (!update(null, wrapper)) {
            save(entity);
        }
    }
}




