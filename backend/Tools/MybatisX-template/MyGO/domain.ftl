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
    private ${field.shortTypeName} ${field.fieldName};
</#list>
}