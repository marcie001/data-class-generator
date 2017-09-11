package ${packageName};

<#list imports as imp>
import ${imp};
</#list>

/**
 * class for the ${classNameAsSnakeCase} database table.
 *
 */
<#list classAnnotations as annotation>
${annotation.javaLiteral}
</#list>
public class ${className}${implementsLiteral} {

<#list fields as field>
	<#list field.fieldAnnotations as annotation>
	${annotation.javaLiteral}
	</#list>
	${field.definitionLiteral};

</#list>
}