package com.example.dataclassgenerator.singularizer;

import java.util.regex.Pattern;

public class EndsWithIesRule extends RegexpRule {

	public EndsWithIesRule() {
		super(Pattern.compile("ies$"), "y");
	}

}
