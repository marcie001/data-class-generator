package com.example.dataclassgenerator.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

/**
 * クラスファイルを書き出す.
 * 
 * @author marcie
 *
 */
@Component
public class ClassWriter {

	private final GeneratorSetting setting;

	private final Configuration freeMarkerConfiguration;

	@Autowired
	public ClassWriter(GeneratorSetting setting, Configuration freeMarkerConfiguration) {
		this.setting = setting;
		this.freeMarkerConfiguration = freeMarkerConfiguration;
	}

	public void write(List<JavaClass> javaClasses) throws TemplateNotFoundException, TemplateException,
			MalformedTemplateNameException, ParseException, IOException {
		String fileSeparator = Matcher.quoteReplacement(File.separator);
		for (JavaClass jc : javaClasses) {
			Template t = freeMarkerConfiguration.getTemplate(setting.getTemplateFileName());
			Path dir = Paths.get(setting.getBuildPath(), jc.getPackageName().replaceAll("\\.", fileSeparator));
			dir = Files.createDirectories(dir);
			BufferedWriter w = Files.newBufferedWriter(dir.resolve(jc.getClassName() + ".java"), StandardCharsets.UTF_8,
					StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
			t.process(jc, w);
		}

	}
}
