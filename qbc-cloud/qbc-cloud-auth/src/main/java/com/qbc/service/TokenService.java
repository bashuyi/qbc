package com.qbc.service;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import com.qbc.exception.UnauthorizedException;
import com.qbc.manager.core.TokenManager;
import com.qbc.openinterface.OpenInterface;
import com.qbc.openinterface.OpenInterfaceMethod;
import com.qbc.openinterface.OpenInterfaceResponse;

@Validated
@OpenInterface
public class TokenService {

	@Autowired
	private TokenManager tokenManager;

	@OpenInterfaceMethod
	public OpenInterfaceResponse<String> createToken(@NotEmpty String username, @NotEmpty String password) {
		// TODO 签名密钥，根据用户名从数据库获得
		String secret = "";
		// 生成Token
		String token = tokenManager.createToken(username, secret);

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
