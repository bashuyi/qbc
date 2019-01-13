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
import com.qbc.dao.SysUserDAO;
import com.qbc.dao.SysUserDO;
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
	private SysUserDAO sysUserDAO;

	@ApiOperation(displayName = "创建Token")
	public ApiResponse<String> createToken(@ApiParam(displayName = "用户名") @NotEmpty String username,
			@ApiParam(displayName = "密码") @NotEmpty String password) {
		SysUserDO sysUserDO = sysUserDAO.findByUsernameAndDeletedFalse(username);

		Assert.notNull(sysUserDO, "createToken.username: unknown");
		Assert.isTrue(StringUtils.equals(password, sysUserDO.getPassword()), "createToken.password: bad");

		// 生成Token
		String token = tokenManager.createToken(username, sysUserDO.getSecret());

		return ApiResponse.ok(token);
	}

	@ApiOperation(displayName = "验证Token")
	public ApiMapResponse verifyToken(ApiMapResponse openInterfaceMapResponse,
			@ApiParam(displayName = "Token") @NotEmpty String token,
			@ApiParam(displayName = "应用名") @NotEmpty String applicationName,
			@ApiParam(displayName = "API名") @NotEmpty String apiName,
			@ApiParam(displayName = "API操作名") @NotEmpty String operationName) {
		// 用户名
		String username = tokenManager.getAudience(token);

		SysUserDO sysUserDO = sysUserDAO.findByUsernameAndDeletedFalse(username);

		Assert.notNull(sysUserDO, "createToken.username: unknown");

		tokenManager.verifyToken(token, sysUserDO.getSecret());

		boolean hasPermission = authManager.hasPermission(username, applicationName, apiName, operationName);

		Assert.isTrue(hasPermission, "createToken.username: no permission");

		openInterfaceMapResponse.put("userId", String.valueOf(sysUserDO.getId()));
		openInterfaceMapResponse.put("username", username);

		return openInterfaceMapResponse;
	}

	@ApiOperation(displayName = "失效Token")
	public void revokeToken() {
	}

}
