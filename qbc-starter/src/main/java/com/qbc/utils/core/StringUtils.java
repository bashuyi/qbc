package com.qbc.utils.core;

import java.util.Map;

import org.apache.commons.text.StringSubstitutor;

import com.google.common.base.CaseFormat;

import lombok.experimental.UtilityClass;

/**
 * 字符串工具类
 *
 * @author Ma
 */
@UtilityClass
public class StringUtils {

	/**
	 * 转换包名为路径名
	 * 
	 * @param packageName 包名
	 * @return 路径名
	 */
	public String packageNameToPathName(String packageName) {
		return org.apache.commons.lang3.StringUtils.replace(packageName, ".", "/");
	}

	/**
	 * 转换字符串的命名法
	 * 
	 * @param text 转换前字符串
	 * @param from 转换前命名法
	 * @param to   转换后命名法
	 * @return 转换后字符串
	 */
	public String caseFormat(String text, CaseFormat from, CaseFormat to) {
		return from.to(to, text);
	}

	/**
	 * 替换模板的参数，生成字符串
	 * 
	 * @param source 模板
	 * @param param  模板参数
	 * @return 替换后字符串
	 */
	public String format(Object source, Map<String, Object> param) {
		return StringSubstitutor.replace(source, param);
	}

}
