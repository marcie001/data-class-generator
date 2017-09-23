package com.example.dataclassgenerator.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.example.dataclassgenerator.singularizer.Singularizer;

/**
 * データベースの情報から Java の情報に変換する converter.
 * 
 * @author marcie
 *
 */
@Component
@ConditionalOnProperty(name = "generator.data-class-type", havingValue = "plain")
public class PlainConverter extends Converter {

	@Autowired
	public PlainConverter(Singularizer singularizer, String generatedDateLiteral) {
		super(singularizer, generatedDateLiteral);
	}

}
