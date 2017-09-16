package com.example.dataclassgenerator.singularizer;

import java.util.regex.Pattern;

public class EndsWithEsRule extends RegexpRule {

	public EndsWithEsRule() {
		super(Pattern.compile("es$"), "");
	}

}
