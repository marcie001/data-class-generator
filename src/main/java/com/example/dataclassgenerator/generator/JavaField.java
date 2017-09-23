package com.example.dataclassgenerator.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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

	private static final String PREMITIVE_ARRAY_PATTERN = "^(byte|short|int|long|float|double|boolean|char)\\[\\]$";

	private static final String ARRAY_PATTERN = "[]";

	private final List<JavaAnnotation> fieldAnnotations = new ArrayList<>();

	private final boolean staticField;

	private final boolean finalField;

	private final String type;

	private final String name;

	private final String initialValue;

	public String getInitialValueWithEqual() {
		if (StringUtils.isEmpty(getInitialValue())) {
			return "";
		}
		return " = " + getInitialValue();
	}

	/**
	 * Return true, if this field can be initialized in constructor. Return
	 * false, if not.
	 * 
	 * @return Return true, if this field can be initialized in constructor.
	 *         Return false, if not.
	 */
	public boolean isInitializable() {
		if (isStaticField()) {
			return false;
		}
		if (isFinalField() && StringUtils.isEmpty(getInitialValue())) {
			return false;
		}
		return true;
	}

	public boolean isPremitiveArray() {
		return Pattern.matches(PREMITIVE_ARRAY_PATTERN, type);
	}

	public boolean isArray() {
		return type.endsWith(ARRAY_PATTERN);
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

	/**
	 * Return true, if this field is an instance field. Return false, if not.
	 * 
	 * @return Return true, if this field is an instance field. Return false, if
	 *         not.
	 */
	public boolean isInstanceField() {
		return !isStaticField();
	}
}
