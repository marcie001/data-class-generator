package com.example.dataclassgenerator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ResourceUtils;

import com.example.dataclassgenerator.generator.DataTypeConverter;
import com.example.dataclassgenerator.generator.GeneratorSetting;
import com.example.dataclassgenerator.generator.MapDataTypeConverter;
import com.example.dataclassgenerator.singularizer.EndsWithEsRule;
import com.example.dataclassgenerator.singularizer.EndsWithIesRule;
import com.example.dataclassgenerator.singularizer.EndsWithIiRule;
import com.example.dataclassgenerator.singularizer.EndsWithSRule;
import com.example.dataclassgenerator.singularizer.IrregularRule;
import com.example.dataclassgenerator.singularizer.Rule;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

@SpringBootApplication
@EnableConfigurationProperties({ GeneratorSetting.class })
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
	DataTypeConverter postgresqlMapDataTypeConverter(GeneratorSetting setting) {
		return new MapDataTypeConverter(setting.getConversionMap());
	}

	@Bean
	List<Rule> rules() {
		return Arrays.asList(new IrregularRule(), new EndsWithIiRule(), new EndsWithIesRule(), new EndsWithEsRule(),
				new EndsWithSRule());
	}

	@Bean
	Clock clock() {
		return Clock.systemDefaultZone();
	}

	@Bean
	OffsetDateTime generatedDate(Clock clock) {
		return OffsetDateTime.ofInstant(clock.instant(), clock.getZone());
	}

	@Bean
	String generatedDateLiteral(OffsetDateTime generatedDate) {
		return "\"" + generatedDate.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME) + "\"";
	}

}
