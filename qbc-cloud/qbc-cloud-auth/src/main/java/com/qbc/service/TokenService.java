package com.qbc.service;

import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import com.qbc.dao.SysUserDAO;
import com.qbc.dao.SysUserDO;
import com.qbc.manager.core.TokenManager;
import com.qbc.openinterface.OpenInterface;
import com.qbc.openinterface.OpenInterfaceMethod;
import com.qbc.openinterface.OpenInterfaceResponse;

@Validated
@OpenInterface
public class TokenService {

	@Autowired
	private TokenManager tokenManager;

	@Autowired
	private SysUserDAO sysUserDAO;

	@OpenInterfaceMethod
	public OpenInterfaceResponse<String> createToken(@NotEmpty String username, @NotEmpty String password) {
		SysUserDO sysUserDO = sysUserDAO.findByUsername(username);

		Assert.notNull(sysUserDO, "createToken.username: unknown");
		Assert.isTrue(StringUtils.equals(password, sysUserDO.getPassword()), "createToken.password: bad");

		// 生成Token
		String token = tokenManager.createToken(username, sysUserDO.getSecret());

		return OpenInterfaceResponse.ok(token);
	}

	@OpenInterfaceMethod
	public OpenInterfaceResponse<String> verifyToken(@NotEmpty String token) {
		// 用户名
		String username = tokenManager.getAudience(token);

		SysUserDO sysUserDO = sysUserDAO.findByUsername(username);

		Assert.notNull(sysUserDO, "createToken.username: unknown");

		tokenManager.verifyToken(token, sysUserDO.getSecret());

		return OpenInterfaceResponse.ok(username);
	}

	@OpenInterfaceMethod
	public void revokeToken(@NotEmpty String token) {
	}

}
