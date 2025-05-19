package com.example.dubboImpl;

import com.boneLight.DAO.dto.InvokeApiDTO;
import com.boneLight.dubboService.ApiDServicer;
import com.example.DAO.service.ApiListService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class ApiDImpl implements ApiDServicer {
    @Resource
    private ApiListService apiListService;
    /*更新接口调用次数*/
    @Override
    public void invokeApi(InvokeApiDTO invokeApiDTO){
        apiListService.invokeApi(invokeApiDTO);
    }
}
