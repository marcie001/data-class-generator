package com.example.dataclassgenerator.singularizer;

import java.util.regex.Pattern;

public class EndsWithIiRule extends RegexpRule {

	public EndsWithIiRule() {
		super(Pattern.compile("ii$"), "us");
	}

}
