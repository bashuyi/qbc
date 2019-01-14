package com.qbc.constant;

/**
 * 日志格式模板
 *
 * @author Ma
 */
public interface LogPattens {

	String API_START = String.join(System.lineSeparator(), "",
			//
			"┏━━━━━API调用开始━━━━━",
			//
			"┣ 接口名称：{}",
			//
			"┣ 接口方法：{}",
			//
			"┣ 接口参数：{}",
			//
			"┗━━━━━API调用开始━━━━━", "");

	String API_END = String.join(System.lineSeparator(), "",
			//
			"┏━━━━━API调用结束━━━━━",
			//
			"┣ 响应代码：{}",
			//
			"┣ 响应信息：{}",
			//
			"┣ 响应内容：{}",
			//
			"┗━━━━━API调用结束━━━━━", "");

}
