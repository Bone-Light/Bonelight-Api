package com.example.DAO.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.boneLight.DAO.dto.InvokeApiDTO;
import com.example.DAO.dto.ApiDTOs.AddApiDTO;
import com.example.DAO.dto.ApiDTOs.DeleteApiDTO;
import com.example.DAO.dto.ApiDTOs.SelectApiListDTO;
import com.example.DAO.dto.ApiDTOs.UpdateApiListInfoDTO;
import com.example.DAO.dto.PageDTO;
import com.example.DAO.entity.ApiList;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.DAO.vo.ApiListVOs.GetApiInfoVO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
* @author 吾骨封灯
* @since 2025-05-06 19:40:48
*/
public interface ApiListService extends IService<ApiList> {

    /** 添加 Api 接口 - 管理*/
    void addApi(AddApiDTO addApiDTO);

    /** 删除 Api 接口 - 管理*/
    void deleteApi(DeleteApiDTO deleteApiDTO);

    /** 修改 Api 信息*/
    void updateApiInfo(UpdateApiListInfoDTO updateApiListInfoDTO);

    /** 获取 Api 信息*/
    GetApiInfoVO getApiInfo(GetApiInfoVO getApiInfoDTO);

    /** 获取 Api 列表*/
    List<ApiList> getApiList();

    /** 获取 Api 列表页*/
    IPage<ApiList> getApiListPage(PageDTO pageDTO);

    /** 搜索 Api */
    List<ApiList> selectApiList(SelectApiListDTO selectApiListDTO);

    /** dubbo 更新调用次数 */
    void invokeApi(InvokeApiDTO invokeApiDTO);
}
