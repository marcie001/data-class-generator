package com.example.dataclassgenerator.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import com.example.dataclassgenerator.singularizer.Singularizer;
import com.google.common.base.CaseFormat;

public abstract class Converter {

	private final Singularizer singularizer;

	public Converter(Singularizer singularizer) {
		this.singularizer = singularizer;
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

	protected abstract void configureClassAnnotations(JavaClass jc);

}