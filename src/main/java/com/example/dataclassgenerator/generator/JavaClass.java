package com.example.dataclassgenerator.generator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import com.google.common.base.CaseFormat;

import lombok.Builder;
import lombok.Data;

/**
 * 生成する Java のクラスを表すモデル.
 * 
 * @author marcie
 *
 */
@Data
@Builder
public class JavaClass {

	private static final Pattern javaLangPackagePattern = Pattern.compile("^java\\.lang\\.[^\\.]+$");

	private final String packageName;

	private final Set<String> imports = new TreeSet<>();

	private final List<JavaAnnotation> classAnnotations = new ArrayList<>();

	private final String className;

	private final Set<String> interfaces = new TreeSet<>();

	private final List<JavaField> fields = new ArrayList<>();

	public String getImplementsLiteral() {
		if (interfaces.size() == 0) {
			return "";
		}
		return interfaces.stream().map(StringUtils::unqualify).collect(Collectors.joining(", ", " implements ", ""));
	}

	public void addToImports(String className) {
		if (!className.contains(".")) {
			return;
		}
		if (belongsToJavaLangPackage(className)) {
			return;
		}
		imports.add(className);
	}

	public void addToClassAnnotations(JavaAnnotation annotation) {
		classAnnotations.add(annotation);
		addToImports(annotation.getName());
	}

	public String getClassNameAsSnakeCase() {
		return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, className);
	}

	public void addToInterfaces(String interfaceName) {
		interfaces.add(interfaceName);
		addToImports(interfaceName);
	}

	public void addToFields(JavaField field) {
		fields.add(field);
		addToImports(field.getType());
	}

	protected JavaClass configureSerializable() {
		this.addToInterfaces(Serializable.class.getName());
		this.addToFields(JavaField.builder().staticField(true).finalField(true).type("long").name("serialVersionUID")
				.initialValue("1L").build());
		return this;
	}

	private boolean belongsToJavaLangPackage(String className) {
		return javaLangPackagePattern.matcher(className).matches();
	}
}
