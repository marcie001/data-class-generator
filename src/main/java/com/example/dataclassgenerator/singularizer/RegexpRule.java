package com.example.dataclassgenerator.singularizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexpRule implements Rule {

	private final Pattern pattern;

	private final String replacement;

	public RegexpRule(Pattern pattern, String replacement) {
		this.pattern = pattern;
		this.replacement = replacement;
	}

	@Override
	public String apply(String plural) {
		Matcher m = pattern.matcher(plural);
		return m.find() ? m.replaceFirst(replacement) : null;
	}

}
