package com.example.dataclassgenerator.postgresql;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties("postgresql")
@Data
public class PostgresqlSetting {

	private Map<String, String> dataTypes;

}
