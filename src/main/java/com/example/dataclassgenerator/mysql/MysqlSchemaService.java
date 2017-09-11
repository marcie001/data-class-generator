package com.example.dataclassgenerator.mysql;

import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Service;

import com.example.dataclassgenerator.generator.SchemaService;
import com.example.dataclassgenerator.generator.Table;

@Service
@ConditionalOnClass(name = "com.mysql.jdbc.Driver")
public class MysqlSchemaService implements SchemaService {

	@Override
	public List<Table> findAll(String targetSchema) {
		throw new UnsupportedOperationException("MySQL is not supported yet.");
	}

	@Override
	public Map<String, String> dataTypes() {
		throw new UnsupportedOperationException("MySQL is not supported yet.");
	}

}
