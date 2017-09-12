package com.example.dataclassgenerator.generator;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.dataclassgenerator.generator.Column.ColumnBuilder;

/**
 * スキーマの情報を取得するサービス.
 * 
 * @author marcie
 *
 */
@Service
public class SchemaService {

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
			long maxLength = rs.getLong("character_maximum_length");
			if (maxLength > 0) {
				cb.maxLength(maxLength);
			}
			t.addColumn(cb.build());
		}

		return tables;
	};

	private final NamedParameterJdbcTemplate template;

	@Autowired
	public SchemaService(DataSource dataSource) {
		template = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * targetSchema で指定したスキーマのテーブルの情報を検索する.
	 * 
	 * @param targetSchema
	 *            テーブルの情報を取得するスキーマ
	 * @return テーブルの情報
	 */
	public List<Table> findAll(String targetSchema) {
		MapSqlParameterSource params = new MapSqlParameterSource("tableSchema", targetSchema);
		return template.query(SQL, params, extractor);
	}
}
