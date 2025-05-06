package ${baseInfo.packageName};

import ${tableClass.fullClassName};
<#if baseService??&&baseService!="">
import ${baseService};
    <#list baseService?split(".") as simpleName>
        <#if !simpleName_has_next>
            <#assign serviceSimpleName>${simpleName}</#assign>
        </#if>
    </#list>
</#if>
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 吾骨封灯
* @since ${.now?string('yyyy-MM-dd HH:mm:ss')}
*/
public interface ${baseInfo.fileName} extends IService<${tableClass.shortClassName}> {

}
