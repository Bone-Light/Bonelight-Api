package com.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.DAO.dto.ApiDTOs.AddApiDTO;
import com.example.DAO.dto.ApiDTOs.DeleteApiDTO;
import com.example.DAO.dto.ApiDTOs.SelectApiListDTO;
import com.example.DAO.dto.ApiDTOs.UpdateApiListInfoDTO;
import com.example.DAO.dto.PageDTO;
import com.example.DAO.entity.ApiList;
import com.example.DAO.service.ApiListService;
import com.example.DAO.vo.ApiListVOs.GetApiInfoVO;
import com.example.common.RestBean;
import com.example.exception.BusinessException;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


/** apiList 接口 */
@Validated
@RestController
@RequestMapping("/apiList")
public class ApiListController {
    @Resource
    private ApiListService apiListService;

    /** 获取Api列表 */
    @GetMapping("getApiList")
    public RestBean<List<ApiList>> getApiList() {
        return RestBean.success(apiListService.getApiList());
    }

    /** 获取Api列表 - 分页*/
    @GetMapping("getApiListPage")
    public RestBean<IPage<ApiList>> getApiListPage(@RequestBody @Valid PageDTO pageDTO) {
        return RestBean.success(apiListService.getApiListPage(pageDTO));
    }

    /** 搜索Api */
    @GetMapping("selectApiList")
    public RestBean<List<ApiList>> selectApiList(@RequestBody SelectApiListDTO selectApiListDTO){
        return RestBean.success(apiListService.selectApiList(selectApiListDTO));
    }

    /** 修改 Api 信息*/
    @PostMapping("updateApiInfo")
    public RestBean<Void> updateApiInfo(UpdateApiListInfoDTO updateApiListInfoDTO){
        apiListService.updateApiInfo(updateApiListInfoDTO);
        return RestBean.success();
    }

    /** 删除 Api 接口 - 管理*/
    @PostMapping("deleteApi")
    public RestBean<Void> deleteApi(DeleteApiDTO deleteApiDTO){
        this.apiListService.deleteApi(deleteApiDTO);
        return RestBean.success();
    }

    /** 添加 Api 接口 - 管理 */
    @PostMapping("addApi")
    public RestBean<Void> addApi(AddApiDTO addApiDTO){
        this.apiListService.addApi(addApiDTO);
        return RestBean.success();
    }

    /** 获取 Api 信息*/
    @GetMapping("getApiInfo")
    public RestBean<GetApiInfoVO> getApiInfo(GetApiInfoVO getApiInfoDTO){
        return RestBean.success(apiListService.getApiInfo(getApiInfoDTO));
    }
}