package com.example.dataclassgenerator.generator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import lombok.Builder;
import lombok.Data;

/**
 * 生成する Java のフィールドを表すクラス.
 * 
 * @author marcie
 *
 */
@Data
@Builder
public class JavaField {

	private final List<JavaAnnotation> fieldAnnotations = new ArrayList<>();

	private final boolean staticField;

	private final boolean finalField;

	private final String type;

	private final String name;

	private final String initialValue;

	public String getInitialValueWithEqual() {
		if (StringUtils.isEmpty(initialValue)) {
			return "";
		}
		return " = " + initialValue;
	}

	public String getShortType() {
		return StringUtils.unqualify(type);
	}

	public String getCapitalizedName() {
		return StringUtils.capitalize(name);
	}

	public String getDefinitionLiteral() {
		StringBuilder sb = new StringBuilder("private ");
		if (isStaticField()) {
			sb.append("static ");
		}
		if (isFinalField()) {
			sb.append("final ");
		}
		sb.append(getShortType()).append(' ').append(name);
		if (!StringUtils.isEmpty(initialValue)) {
			sb.append(" = ").append(initialValue);
		}
		return sb.toString();
	}
}
