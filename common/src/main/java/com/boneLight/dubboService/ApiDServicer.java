package com.boneLight.dubboService;

import com.boneLight.DAO.dto.InvokeApiDTO;

public interface ApiDServicer {
    /*更新接口调用次数*/
    void invokeApi(InvokeApiDTO invokeApiDTO);
}
