package com.example.dataclassgenerator.singularizer;

import java.util.HashMap;
import java.util.Map;

public class IrregularRule implements Rule {

	private static final Map<String, String> conversionMap = new HashMap<>();
	static {
		conversionMap.put("radii", "radius");
		conversionMap.put("types", "type");
		conversionMap.put("blades", "blade");
		conversionMap.put("pies", "pie");
		conversionMap.put("trees", "tree");
		conversionMap.put("tables", "table");
		conversionMap.put("children", "child");
	}

	@Override
	public String apply(String plural) {
		return conversionMap.get(plural);
	}

}
