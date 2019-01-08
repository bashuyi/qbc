package com.qbc.service;

import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import com.qbc.dao.SysUserDAO;
import com.qbc.dao.SysUserDO;
import com.qbc.exception.UnauthorizedException;
import com.qbc.manager.core.TokenManager;
import com.qbc.manager.core.ValidationManager;
import com.qbc.openinterface.OpenInterface;
import com.qbc.openinterface.OpenInterfaceMethod;
import com.qbc.openinterface.OpenInterfaceResponse;

@Validated
@OpenInterface
public class TokenService {

	@Autowired
	private TokenManager tokenManager;

	@Autowired
	private ValidationManager validationManager;

	@Autowired
	private SysUserDAO sysUserDAO;

	@OpenInterfaceMethod
	public OpenInterfaceResponse<String> createToken(@NotEmpty String username, @NotEmpty String password) {
		SysUserDO sysUserDO = sysUserDAO.findByUsername(username);
		
		// 认证
		validationManager.notNull(sysUserDO);
		validationManager.assertTrue(StringUtils.equals(password, sysUserDO.getPassword()));

		// 生成Token
		String token = tokenManager.createToken(username, sysUserDO.getSecret());

		return OpenInterfaceResponse.ok(token);
	}

	@OpenInterfaceMethod
	public void verifyToken(@NotEmpty String token) {
		// 用户名
		String username = tokenManager.getAudience(token);
		// TODO 签名密钥，根据用户名从数据库获得
		String secret = "";
		try {
			tokenManager.verifyToken(token, secret);
		} catch (Exception e) {
			throw new UnauthorizedException(e);
		}
	}

	@OpenInterfaceMethod
	public OpenInterfaceResponse<String> refreshToken(@NotEmpty String token) {
		// 用户名
		String username = tokenManager.getAudience(token);
		// TODO 签名密钥，根据用户名从数据库获得
		String secret = "";
		try {
			tokenManager.verifyToken(token, secret);
		} catch (Exception e) {
			throw new UnauthorizedException(e);
		}

		// 生成新Token
		String newToken = tokenManager.createToken(username, secret);

		return OpenInterfaceResponse.ok(newToken);
	}

	@OpenInterfaceMethod
	public void revokeToken(@NotEmpty String token) {

	}

}
