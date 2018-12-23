package com.qbc.core.biz;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;

@Component
public class CodeGeneratorBIZ {

	@Autowired
	private Configuration configuration;

	/**
	 * 替换模板的参数，生成字符串
	 * 
	 * @param templateName 模板名
	 * @param param        模板参数
	 * @return 替换后字符串
	 */
	@SneakyThrows
	public String generateToString(String templateName, Map<String, Object> param) {
		Template template = configuration.getTemplate(templateName);
		return FreeMarkerTemplateUtils.processTemplateIntoString(template,
				ObjectUtils.defaultIfNull(param, new HashMap<>()));
	}

	/**
	 * 替换模板的参数，生成文件
	 * 
	 * @param templateName 模板名
	 * @param param        模板参数
	 * @param file         输出文件路径
	 */
	@SneakyThrows
	public void generate(String templateName, Map<String, Object> param, File file) {
		FileUtils.writeStringToFile(file, generateToString(templateName, param), StandardCharsets.UTF_8);
	}

}
