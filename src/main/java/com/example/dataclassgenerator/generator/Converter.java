package com.example.dataclassgenerator.generator;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.CaseFormat;

public abstract class Converter {

	public List<JavaClass> convert(List<Table> tables, DataTypeConverter datatypeConverter, String javaPackage) {
		List<JavaClass> list = new ArrayList<>();
		for (Table t : tables) {
			JavaClass jc = JavaClass.builder()
					.className(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, t.getName().toLowerCase()))
					.packageName(javaPackage).build().configureSerializable();
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

	protected abstract void configureClassAnnotations(JavaClass jc);

}