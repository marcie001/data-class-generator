package com.example.dataclassgenerator.generator;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.Data;

@Component
@ConditionalOnProperty(name = "generator.data-class-type", havingValue = "lombok")
public class LombokConverter extends Converter {

	@Override
	protected void configureClassAnnotations(JavaClass jc) {
		jc.addToClassAnnotations(JavaAnnotation.builder().name(Data.class.getName()).build());
		jc.addToClassAnnotations(JavaAnnotation.builder().name(Builder.class.getName()).build());
	}

}
