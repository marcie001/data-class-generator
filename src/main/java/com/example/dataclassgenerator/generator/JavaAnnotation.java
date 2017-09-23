package com.example.dataclassgenerator.generator;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import lombok.Builder;
import lombok.Data;

/**
 * Java の annotation を表すクラス.
 * 
 * @author marcie
 *
 */
@Data
@Builder
public class JavaAnnotation {

	private final String name;

	private final Map<String, String[]> elements = new LinkedHashMap<>();

	public String getShortName() {
		return StringUtils.unqualify(name);
	}

	public String getJavaLiteral() {
		StringBuilder sb = new StringBuilder("@").append(getShortName());
		if (elements.isEmpty()) {
			return sb.toString();
		}

		return sb.append(elements.entrySet().stream().map(e -> e.getKey() + " = " + toValueLiteral(e.getValue()))
				.collect(Collectors.joining(", ", "(", ")"))).toString();
	}

	public JavaAnnotation addToElements(String name, String value) {
		return addToElements(name, new String[] { value });
	}

	public JavaAnnotation addToElements(String name, String[] values) {
		elements.put(name, values);
		return this;
	}

	private String toValueLiteral(String[] values) {
		if (values.length == 1) {
			return values[0];
		}
		return Arrays.stream(values).collect(Collectors.joining(", ", "{", "}"));
	}
}
