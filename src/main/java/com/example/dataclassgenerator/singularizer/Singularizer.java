package com.example.dataclassgenerator.singularizer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Singularizer {
	private static final Logger logger = LoggerFactory.getLogger(Singularizer.class);

	private final List<Rule> rules;

	@Autowired
	public Singularizer(List<Rule> rules) {
		this.rules = rules;
	}

	public String singularize(String plural) {
		if (plural == null) {
			throw new IllegalArgumentException("plural must not be null.");
		}
		String lcPlural = plural.trim().toLowerCase();
		return rules.stream().map(e -> e.apply(lcPlural)).filter(e -> e != null).findFirst().orElseGet(() -> {
			logger.warn("There are no rules to apply. : " + plural);
			return lcPlural;
		});
	}

}
