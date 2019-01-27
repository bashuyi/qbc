package com.qbc.manager.system;

import java.text.MessageFormat;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.stereotype.Component;

import com.qbc.dao.system.SystemMessageDAO;
import com.qbc.dao.system.SystemMessageDO;
import com.qbc.manager.core.DataSourceManager;

@Component
public class MessageManager extends AbstractMessageSource {

	@Autowired
	private DataSourceManager dataSourceManager;

	@Autowired
	private SystemMessageDAO systemMessageDAO;

	@Autowired(required = false)
	private MessageManager messageManager;

	@Override
	protected MessageFormat resolveCode(String code, Locale locale) {
		SystemMessageDO messageDO = systemMessageDAO.findByCodeAndLocaleAndDeletedFalse(code, locale.toString());
		if (messageDO == null) {
			return null;
		}
		return new MessageFormat(messageDO.getText(), locale);
	}

}
