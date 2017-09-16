package com.example.dataclassgenerator.singularizer;

import java.util.regex.Pattern;

public class EndsWithSRule extends RegexpRule {

	public EndsWithSRule() {
		super(Pattern.compile("s$"), "");
	}

}
