package ${domain.packageName};

<#list tableClass.importList as fieldType>${"\n"}import ${fieldType};</#list>
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
@Data
@TableName("${tableClass.tableName}")
@AllArgsConstructor
@NoArgsConstructor
public class ${tableClass.shortClassName} {
<#list tableClass.allFields as field>
    //${field.remark!}
    <#-- 判断字段是否为主键 -->
    <#if field.isPrimaryKey>
    @TableId(type = IdType.AUTO<#if field.columnName != "id">, value = "${field.columnName}"</#if>)
    <#else>
    @TableField("${field.columnName}") <#-- 非主键字段建议添加 @TableField -->
    </#if>
    private ${field.shortTypeName} ${field.fieldName};
</#list>
}