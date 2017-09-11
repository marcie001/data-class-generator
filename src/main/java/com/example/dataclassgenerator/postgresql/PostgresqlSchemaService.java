package com.example.dataclassgenerator.postgresql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.dataclassgenerator.generator.Column;
import com.example.dataclassgenerator.generator.Column.ColumnBuilder;
import com.example.dataclassgenerator.generator.SchemaService;
import com.example.dataclassgenerator.generator.Table;

@Service
@ConditionalOnClass(name = "org.postgresql.Driver")
public class PostgresqlSchemaService implements SchemaService {

	private static final List<String> CHARACTER_TYPES = Collections
			.unmodifiableList(Arrays.asList("character varying", "character"));

	private static final String SQL = "SELECT * FROM information_schema.columns WHERE table_schema = :tableSchema order by table_name, ordinal_position";

	private static final ResultSetExtractor<List<Table>> extractor = rs -> {
		List<Table> tables = new ArrayList<>();

		Table t = null;
		while (rs.next()) {
			String tableName = rs.getString("table_name");
			if (t == null || !t.getName().equals(tableName)) {
				t = Table.builder().name(tableName).build();
				tables.add(t);
			}

			String dataType = rs.getString("data_type");
			ColumnBuilder cb = Column.builder().dataType(dataType).name(rs.getString("column_name"))
					.nullable("YES".equals(rs.getString("is_nullable")));
			if (CHARACTER_TYPES.contains(dataType)) {
				cb.maxLength(rs.getInt("character_maximum_length"));
			}
			t.addColumn(cb.build());
		}

		return tables;
	};

	private final NamedParameterJdbcTemplate template;

	private final PostgresqlSetting setting;

	@Autowired
	public PostgresqlSchemaService(DataSource dataSource, PostgresqlSetting setting) {
		template = new NamedParameterJdbcTemplate(dataSource);
		this.setting = setting;
	}

	@Override
	public List<Table> findAll(String targetSchema) {
		MapSqlParameterSource params = new MapSqlParameterSource("tableSchema", targetSchema);
		return template.query(SQL, params, extractor);
	}

	@Override
	public Map<String, String> dataTypes() {
		return setting.getDataTypes().entrySet().stream()
				.collect(Collectors.toMap(e -> e.getKey().replaceAll("-", " "), Entry::getValue));
	}

}
