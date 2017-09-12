package com.example.dataclassgenerator.generator;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandMain implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(CommandMain.class);

	private final GeneratorSetting generatorSetting;

	private final SchemaService schemaService;

	private final DataTypeConverter dataTypeConverter;

	private final Converter converter;

	private final ClassWriter classWriter;

	@Autowired
	public CommandMain(GeneratorSetting generatorSetting, SchemaService schemaService,
			DataTypeConverter dataTypeConverter, Converter converter, ClassWriter classWriter) {
		this.generatorSetting = generatorSetting;
		this.schemaService = schemaService;
		this.dataTypeConverter = dataTypeConverter;
		this.converter = converter;
		this.classWriter = classWriter;
	}

	@Override
	public void run(String... arg0) throws Exception {
		List<Table> tables = schemaService.findAll(generatorSetting.getTargetSchema());

		List<JavaClass> javaClasses = converter.convert(tables, dataTypeConverter, generatorSetting.getJavaPackage());

		logger.info(javaClasses.toString());

		classWriter.write(javaClasses);
	}
}
