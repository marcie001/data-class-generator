package com.example.dataclassgenerator.generator;

import java.util.List;
import java.util.Map;

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

	private final Converter converter;

	private final ClassWriter classWriter;

	@Autowired
	public CommandMain(GeneratorSetting generatorSetting, SchemaService schemaService, Converter converter,
			ClassWriter classWriter) {
		this.generatorSetting = generatorSetting;
		this.schemaService = schemaService;
		this.converter = converter;
		this.classWriter = classWriter;
	}

	@Override
	public void run(String... arg0) throws Exception {
		List<Table> tables = schemaService.findAll(generatorSetting.getTargetSchema());
		Map<String, String> dataTypes = schemaService.dataTypes();

		List<JavaClass> javaClasses = converter.convert(tables, dataTypes, generatorSetting.getJavaPackage());

		logger.info(javaClasses.toString());

		classWriter.write(javaClasses);
	}
}
