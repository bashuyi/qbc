package com.qbc.utils.core;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import lombok.experimental.UtilityClass;

/**
 * 用户上下文
 *
 * @author Ma
 */
@UtilityClass
public class UserUtils {

	private final ThreadLocal<Long> userIdThreadLocal = new ThreadLocal<>();

	private final ThreadLocal<String> usernameThreadLocal = new ThreadLocal<>();

	public final Long getUserId() {
		return userIdThreadLocal.get();
	}

	public final void setUserId(Long userId) {
		userIdThreadLocal.set(userId);
		MDC.put("userId", Objects.toString(userId, ""));
	}

	public final String getUsername() {
		return usernameThreadLocal.get();
	}

	public final void setUsername(String username) {
		usernameThreadLocal.set(username);
		MDC.put("username", StringUtils.defaultString(username));
	}

	public final void removeAll() {
		userIdThreadLocal.remove();
		usernameThreadLocal.remove();
		MDC.clear();
	}

}
