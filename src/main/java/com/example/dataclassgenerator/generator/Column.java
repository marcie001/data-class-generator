package com.example.dataclassgenerator.generator;

import lombok.Builder;
import lombok.Data;

/**
 * テーブルのカラムを表すクラス.
 * 
 * @author marcie
 *
 */
@Data
@Builder
public class Column {

	/**
	 * データベースのデータ型.
	 */
	private final String dataType;

	/**
	 * 名前.
	 */
	private final String name;

	/**
	 * null 許容の時 true, そうでないとき false.
	 */
	private final boolean nullable;

	/**
	 * 長さ.
	 */
	private final Long maxLength;
}
