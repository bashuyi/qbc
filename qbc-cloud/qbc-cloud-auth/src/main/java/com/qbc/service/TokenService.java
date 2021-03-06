package com.qbc.service;

import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import com.qbc.api.ApiMapResponse;
import com.qbc.api.ApiResponse;
import com.qbc.api.annotation.Api;
import com.qbc.api.annotation.ApiOperation;
import com.qbc.api.annotation.ApiParam;
import com.qbc.dao.auth.AuthUserDAO;
import com.qbc.dao.auth.AuthUserDO;
import com.qbc.manager.AuthManager;
import com.qbc.manager.core.TokenManager;

/**
 * Token服务
 *
 * @author Ma
 */
@Api(displayName = "Token服务")
@Validated
public class TokenService {

	@Autowired
	private TokenManager tokenManager;

	@Autowired
	private AuthManager authManager;

	@Autowired
	private AuthUserDAO authUserDAO;

	@ApiOperation(displayName = "创建Token")
	public ApiResponse<String> createToken(@ApiParam(displayName = "用户名") @NotEmpty String username,
			@ApiParam(displayName = "密码") @NotEmpty String password) {
		// 认证用户名和密码
		AuthUserDO authUserDO = authUserDAO.findByUsernameAndDeletedFalse(username);
		Assert.notNull(authUserDO, "unknown username");
		Assert.isTrue(StringUtils.equals(password, authUserDO.getPassword()), "bad password");

		// 生成Token
		String token = tokenManager.createToken(username, authUserDO.getSecret());
		return ApiResponse.ok(token);
	}

	@ApiOperation(displayName = "验证Token")
	public ApiResponse<?> verifyToken(ApiMapResponse openInterfaceMapResponse,
			@ApiParam(displayName = "Token") @NotEmpty String token,
			@ApiParam(displayName = "应用名") @NotEmpty String applicationName,
			@ApiParam(displayName = "API名") @NotEmpty String apiName,
			@ApiParam(displayName = "API操作名") @NotEmpty String operationName) {
		// 获得用户信息
		String username = tokenManager.getAudience(token);
		AuthUserDO authUserDO = authUserDAO.findByUsernameAndDeletedFalse(username);
		Assert.notNull(authUserDO, "unknown username");

		// 验证Token
		tokenManager.verifyToken(token, authUserDO.getSecret());

		// 鉴权
		boolean hasPermission = authManager.hasPermission(username, applicationName, apiName, operationName);
		if (hasPermission) {
			return ApiMapResponse.unauthorized();
		}

		openInterfaceMapResponse.put("userId", String.valueOf(authUserDO.getId()));
		openInterfaceMapResponse.put("username", username);
		return openInterfaceMapResponse;
	}

	@ApiOperation(displayName = "失效Token")
	public void revokeToken() {
	}

}
