package com.example.dataclassgenerator.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Generated;

import org.springframework.util.StringUtils;

import com.example.dataclassgenerator.singularizer.Singularizer;
import com.google.common.base.CaseFormat;

public class Converter {

	private final Singularizer singularizer;

	private final String generatedDateLiteral;

	public Converter(Singularizer singularizer, String generatedDateLiteral) {
		this.singularizer = singularizer;
		this.generatedDateLiteral = generatedDateLiteral;
	}

	public List<JavaClass> convert(List<Table> tables, DataTypeConverter datatypeConverter, String javaPackage) {
		List<JavaClass> list = new ArrayList<>();
		for (Table t : tables) {

			JavaClass jc = JavaClass.builder().classJavaDoc(generateClassJavaDoc(t.getName()))
					.className(toJavaClassName(t.getName())).packageName(javaPackage).build().configureSerializable();
			list.add(jc);

			for (Column c : t.getColumns()) {
				jc.addToFields(JavaField.builder()
						.name(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, c.getName().toLowerCase()))
						.type(datatypeConverter.convert(c.getDataType())).build());
			}

			configureClassAnnotations(jc);
		}
		return list;

	}

	protected String toJavaClassName(String tableName) {
		String[] tableNameArray = tableName.toLowerCase().split("_");
		tableNameArray[tableNameArray.length - 1] = singularizer.singularize(tableNameArray[tableNameArray.length - 1]);
		return Arrays.stream(tableNameArray).map(StringUtils::capitalize).collect(Collectors.joining());
	}

	protected String generateClassJavaDoc(String tableName) {
		return String.format("class for the %s database table.", tableName);
	}

	protected void configureClassAnnotations(JavaClass jc) {
		JavaAnnotation ja = JavaAnnotation.builder().name(Generated.class.getName()).build()
				.addToElements("value", "\"com.example.dataclassgenerator.DataClassGeneratorApplication\"")
				.addToElements("date", generatedDateLiteral);
		jc.addToClassAnnotations(ja);
	}

}