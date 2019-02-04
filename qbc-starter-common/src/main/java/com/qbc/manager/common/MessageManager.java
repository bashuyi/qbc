package com.qbc.manager.common;

import java.text.MessageFormat;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.stereotype.Component;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.qbc.dao.common.CommonMessageDAO;
import com.qbc.dao.common.CommonMessageDO;

@Component
@DS("common")
public class MessageManager extends AbstractMessageSource {

	@Autowired
	private CommonMessageDAO commonMessageDAO;

	@Autowired(required = false)
	private MessageManager messageManager;

	@Override
	protected MessageFormat resolveCode(String code, Locale locale) {
		CommonMessageDO commonMessageDO = messageManager.findByCodeAndLocale(code, locale.toString());
		return commonMessageDO == null ? null : new MessageFormat(commonMessageDO.getContent(), locale);
	}

	@Cacheable(value = "MESSAGE")
	public CommonMessageDO findByCodeAndLocale(String code, String lang) {
		return commonMessageDAO.findByCodeAndLocaleAndDeletedFalse(code, lang);
	}

}
