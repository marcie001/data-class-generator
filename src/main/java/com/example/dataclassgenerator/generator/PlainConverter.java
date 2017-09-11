package com.example.dataclassgenerator.generator;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * データベースの情報から Java の情報に変換する converter.
 * 
 * @author marcie
 *
 */
@Component
@ConditionalOnProperty(name = "generator.data-class-type", havingValue = "plain")
public class PlainConverter extends Converter {

	@Override
	protected void configureClassAnnotations(JavaClass jc) {
		// do nothing.
	}

}
