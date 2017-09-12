package com.example.dataclassgenerator.generator;

/**
 * Data type converter.
 * 
 * @author marcie
 *
 */
public interface DataTypeConverter {

	/**
	 * Convert RDBMS data type to Java class name.
	 * 
	 * @param dataType
	 *            data type of RDBMS
	 * @return Java class name
	 */
	String convert(String dataType);
}
