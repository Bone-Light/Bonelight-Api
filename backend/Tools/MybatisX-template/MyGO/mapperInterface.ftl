package ${mapperInterface.packageName};

import ${tableClass.fullClassName};
<#if tableClass.pkFields??>
    <#list tableClass.pkFields as field><#assign pkName>${field.shortTypeName}</#assign></#list>
</#if>
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 吾骨封灯
* @since ${.now?string('yyyy-MM-dd HH:mm:ss')}
*/
@Mapper
public interface ${mapperInterface.fileName} extends BaseMapper<${tableClass.shortClassName}> {

}




