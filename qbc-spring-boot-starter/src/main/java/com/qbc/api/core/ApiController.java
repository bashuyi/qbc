package com.qbc.api.core;

import java.util.Map;

import org.apache.commons.beanutils.MethodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ApiController {

	@Autowired
	private ApplicationContext applicationContext;

	@PostMapping(value = "${qbc.api.url}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String dispatch(@RequestBody RequestBean requestBean) {
		String apiName = requestBean.getApi();
		String apiMethodName = requestBean.getMethod();
		Map<String, Object> args = requestBean.getArgs();
		Object apiBean = applicationContext.getBean(apiName);
		if (apiBean != null) {
			Class<?> classType = apiBean.getClass();
			try {
				MethodUtils.invokeMethod(apiBean, apiMethodName, args);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "";
	}

}
