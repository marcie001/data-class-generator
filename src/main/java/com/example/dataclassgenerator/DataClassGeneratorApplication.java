package com.example.dataclassgenerator;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ResourceUtils;

import com.example.dataclassgenerator.generator.DataTypeConverter;
import com.example.dataclassgenerator.generator.GeneratorSetting;
import com.example.dataclassgenerator.generator.MapDataTypeConverter;
import com.example.dataclassgenerator.mysql.MysqlSetting;
import com.example.dataclassgenerator.postgresql.PostgresqlSetting;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

@SpringBootApplication
@EnableConfigurationProperties({ GeneratorSetting.class, PostgresqlSetting.class, MysqlSetting.class })
public class DataClassGeneratorApplication {

	private static final Logger logger = LoggerFactory.getLogger(DataClassGeneratorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DataClassGeneratorApplication.class, args);
	}

	@Bean
	Configuration freeMarkerConfiguration() throws FileNotFoundException, IOException {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_26);
		cfg.setDirectoryForTemplateLoading(ResourceUtils.getFile("classpath:templates"));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setLogTemplateExceptions(false);
		return cfg;
	}

	@Bean
	@ConditionalOnClass(name = "org.postgresql.Driver")
	DataTypeConverter postgresqlMapDataTypeConverter(PostgresqlSetting setting) {
		return new MapDataTypeConverter(setting.getConversionMap());
	}

	@Bean
	@ConditionalOnClass(name = "com.mysql.jdbc.Driver")
	DataTypeConverter mysqlMapDataTypeConverter(MysqlSetting setting) {
		return new MapDataTypeConverter(setting.getConversionMap());
	}

}
