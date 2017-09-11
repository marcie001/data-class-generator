package com.example.dataclassgenerator.generator;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * テーブルを表すクラス.
 * 
 * @author marcie
 *
 */
@Data
@Builder
public class Table {

	private final String name;

	private final List<Column> columns = new ArrayList<>();

	/**
	 * カラム情報を追加する.
	 * 
	 * @param column
	 *            カラム情報
	 */
	public void addColumn(Column column) {
		columns.add(column);
	}
}
