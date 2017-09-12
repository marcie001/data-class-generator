package com.example.dataclassgenerator.generator;

import java.util.Map;
import java.util.Optional;

/**
 * 変換表による変換を行う.
 * 
 * @author marcie
 *
 */
public class MapDataTypeConverter implements DataTypeConverter {

	private final Map<String, String> conversionMap;

	public MapDataTypeConverter(Map<String, String> conversionMap) {
		this.conversionMap = conversionMap;
	}

	@Override
	public String convert(String dataType) {
		return Optional.ofNullable(conversionMap.get(dataType)).orElseThrow(
				() -> new IllegalArgumentException(String.format("Data type(%s) is not supported.", dataType)));
	}

}
