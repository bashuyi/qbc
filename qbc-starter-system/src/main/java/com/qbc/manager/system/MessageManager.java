package com.qbc.manager.system;

import java.text.MessageFormat;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.stereotype.Component;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.qbc.dao.system.SystemMessageDAO;
import com.qbc.dao.system.SystemMessageDO;

@Component
@DS("system")
public class MessageManager extends AbstractMessageSource {

	@Autowired
	private SystemMessageDAO systemMessageDAO;

	@Autowired(required = false)
	private MessageManager messageManager;

	@Override
	protected MessageFormat resolveCode(String code, Locale locale) {
		SystemMessageDO systemMessageDO = messageManager.findByCodeAndLocale(code, locale.toString());
		if (systemMessageDO == null) {
			return null;
		}
		return new MessageFormat(systemMessageDO.getText(), locale);
	}

	@Cacheable(value = "MESSAGE")
	public SystemMessageDO findByCodeAndLocale(String code, String locale) {
		return systemMessageDAO.findByCodeAndLocaleAndDeletedFalse(code, locale);
	}

}
