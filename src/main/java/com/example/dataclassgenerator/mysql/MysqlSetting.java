package com.example.dataclassgenerator.mysql;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties("mysql")
@Data
public class MysqlSetting {

	private Map<String, String> conversionMap;

}
