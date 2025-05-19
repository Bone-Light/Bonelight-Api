package com.example.DAO.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.DAO.dto.ApiDTOs.AddApiDTO;
import com.example.DAO.dto.ApiDTOs.DeleteApiDTO;
import com.example.DAO.dto.ApiDTOs.SelectApiListDTO;
import com.example.DAO.dto.PageDTO;
import com.example.DAO.vo.ApiListVOs.GetApiInfoVO;
import com.example.DAO.dto.ApiDTOs.UpdateApiListInfoDTO;
import com.example.DAO.entity.ApiList;
import com.example.DAO.service.ApiListService;
import com.example.DAO.mapper.ApiListMapper;
import com.example.exception.BusinessException;
import com.boneLight.DAO.dto.InvokeApiDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
* @author 吾骨封灯
* @since 2025-05-06 19:40:48
*/
@Service
public class ApiListImpl extends ServiceImpl<ApiListMapper, ApiList>
    implements ApiListService{
    /** 添加 Api 接口 - 管理*/
    @PreAuthorize("hasRole='Admin'")
    @Override
    public void addApi(AddApiDTO addApiDTO){
        ApiList apiList = new ApiList();
        BeanUtils.copyProperties(addApiDTO,apiList);
        apiList.setCreateTime(LocalDateTime.now());
        apiList.setUpdateTime(LocalDateTime.now());
        this.save(apiList);
    }

    /** 删除 Api 接口 - 管理*/
    @PreAuthorize("hasRole='Admin'")
    @Override
    public void deleteApi(DeleteApiDTO deleteApiDTO){
        int id = deleteApiDTO.getId();
        if(this.query().eq("id", id).count() == 0) throw new BusinessException(401, "待删除接口不存在");
        this.removeById(id);
    }

    /** 修改 Api 信息*/
    @PreAuthorize("hasRole='Admin'")
    @Override
    public void updateApiInfo(UpdateApiListInfoDTO updateApiListInfoDTO){
        int id = updateApiListInfoDTO.getId();
        if(this.query().eq("id", id).count() == 0)  throw new BusinessException(401, "待更新接口不存在");
        ApiList apiList = new ApiList();
        BeanUtils.copyProperties(updateApiListInfoDTO,apiList);
        apiList.setUpdateTime(LocalDateTime.now());
        this.updateById(apiList);
    }

    /** 获取 Api 信息*/
    @Override
    public GetApiInfoVO getApiInfo(GetApiInfoVO getApiInfoDTO){
        int id = getApiInfoDTO.getId();
        if(this.query().eq("id", id).count() == 0)  throw new BusinessException(401, "接口不存在");
        ApiList apiList = this.query().eq("id", id).one();
        GetApiInfoVO getApiInfoVO = new GetApiInfoVO();
        BeanUtils.copyProperties(apiList,getApiInfoVO);
        return getApiInfoVO;
    }

    /** 获取 Api 列表*/
    @Override
    public List<ApiList> getApiList(){
        return this.list();
    }

    /** 获取 Api 列表页*/
    @Override
    public IPage<ApiList> getApiListPage(PageDTO pageDTO){
        Page<ApiList> page = new Page<>(pageDTO.getCurrentPage(), pageDTO.getSize());
        return this.query().page(page);
    }

    /** 搜索 Api */
    @Override
    public List<ApiList> selectApiList(SelectApiListDTO selectApiListDTO){
        return this.query().like("apiName" , selectApiListDTO.getSelectInfo()).list();
    }
    /** dubbo 更新调用次数 */
    @Override
    public void invokeApi(InvokeApiDTO invokeApiDTO){
        this.update().eq("id", invokeApiDTO.getId()).setIncrBy("totInvoke", 1);
    }
}





