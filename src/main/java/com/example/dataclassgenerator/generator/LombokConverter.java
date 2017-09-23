package com.example.dataclassgenerator.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.example.dataclassgenerator.singularizer.Singularizer;

import lombok.Builder;
import lombok.Data;

@Component
@ConditionalOnProperty(name = "generator.data-class-type", havingValue = "lombok")
public class LombokConverter extends Converter {

	@Autowired
	public LombokConverter(Singularizer singularizer, String generatedDateLiteral) {
		super(singularizer, generatedDateLiteral);
	}

	@Override
	protected void configureClassAnnotations(JavaClass jc) {
		super.configureClassAnnotations(jc);
		jc.addToClassAnnotations(JavaAnnotation.builder().name(Data.class.getName()).build());
		jc.addToClassAnnotations(JavaAnnotation.builder().name(Builder.class.getName()).build());
	}

}
