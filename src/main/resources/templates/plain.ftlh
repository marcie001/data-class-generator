package ${packageName};

<#list imports as imp>
import ${imp};
</#list>

/**
 * ${classJavaDoc}
 *
 */
public class ${className}${implementsLiteral} {

<#list fields as field>
	<#list field.fieldAnnotations as annotation>
	${annotation.javaLiteral}
	</#list>
	${field.definitionLiteral};

</#list>
<#list fields as field>
	<#if !field.staticField>
	public ${field.shortType} get${field.capitalizedName}() {
		return ${field.name};
	}

	</#if>
	<#if !field.finalField && !field.staticField>
	public void set${field.capitalizedName}(${field.shortType} ${field.name}) {
		this.${field.name} = ${field.name};
	}

	</#if>
</#list>
}