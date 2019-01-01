package com.qbc.manager.core;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.qbc.manager.core.DatabaseInfoDTO.TableInfo;
import com.qbc.utils.core.StringUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;

@Component
public class CodeGeneratorManager {

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
				ObjectUtils.defaultIfNull(param, new HashMap<>(0)));
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

	/**
	 * 根据表信息和模板生成代码
	 * 
	 * @param templateName 模板名
	 * @param packageName  包名
	 * @param tableInfo    表信息
	 */
	@SneakyThrows
	public void generate(String templateName, String packageName, TableInfo tableInfo) {
		Map<String, Object> param = PropertyUtils.describe(tableInfo);
		File file = Paths.get("src/main/java", StringUtils.packageNameToPathName(packageName),
				tableInfo.getClassName() + templateName + ".java").toFile();
		generate(templateName, param, file);
	}

	/**
	 * 根据数据库信息和模板生成代码
	 * 
	 * @param templateName    模板名
	 * @param packageName     包名
	 * @param databaseInfoDTO 数据库信息
	 */
	@SneakyThrows
	public void generateAll(String templateName, String packageName, DatabaseInfoDTO databaseInfoDTO) {
		databaseInfoDTO.getTableInfos().forEach(tableInfo -> generate(templateName, packageName, tableInfo));
	}

}
