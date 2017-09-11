package com.example.dataclassgenerator.generator;

import java.util.List;
import java.util.Map;

/**
 * スキーマの情報を取得するサービス.
 * 
 * @author marcie
 *
 */
public interface SchemaService {

	/**
	 * targetSchema で指定したスキーマのテーブルの情報を検索する.
	 * 
	 * @param targetSchema
	 *            テーブルの情報を取得するスキーマ
	 * @return テーブルの情報
	 */
	List<Table> findAll(String targetSchema);

	/**
	 * データベースのデータ型と Java のクラスの変換表を返す.
	 * 
	 * @return key: データベースのデータ型. value: 対応する Java のクラス
	 */
	Map<String, String> dataTypes();
}
