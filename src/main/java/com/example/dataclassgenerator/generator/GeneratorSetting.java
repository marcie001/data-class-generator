package com.example.dataclassgenerator.generator;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

@ConfigurationProperties("generator")
@Data
@Validated
public class GeneratorSetting {

	@NotEmpty
	private String targetSchema;

	@NotEmpty
	private String javaPackage;

	@Pattern(regexp = "(plain|lombok)")
	private String dataClassType;

	@NotEmpty
	private String buildPath;

	public String getTemplateFileName() {
		return dataClassType + ".ftlh";
	}
}
