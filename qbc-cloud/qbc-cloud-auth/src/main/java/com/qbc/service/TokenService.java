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
import com.qbc.dao.SysUserDAO;
import com.qbc.dao.SysUserDO;
import com.qbc.manager.core.TokenManager;

/**
 * Token服务，用户创建Token、验证Token、失效Token
 *
 * @author Ma
 */
@Api
@Validated
public class TokenService {

	@Autowired
	private TokenManager tokenManager;

	@Autowired
	private SysUserDAO sysUserDAO;

	@ApiOperation
	public ApiResponse<String> createToken(@NotEmpty String username, @NotEmpty String password) {
		SysUserDO sysUserDO = sysUserDAO.findByUsername(username);

		Assert.notNull(sysUserDO, "createToken.username: unknown");
		Assert.isTrue(StringUtils.equals(password, sysUserDO.getPassword()), "createToken.password: bad");

		// 生成Token
		String token = tokenManager.createToken(username, sysUserDO.getSecret());

		return ApiResponse.ok(token);
	}

	@ApiOperation
	public ApiMapResponse verifyToken(ApiMapResponse openInterfaceMapResponse,
			@NotEmpty String token) {
		// 用户名
		String username = tokenManager.getAudience(token);

		SysUserDO sysUserDO = sysUserDAO.findByUsername(username);

		Assert.notNull(sysUserDO, "createToken.username: unknown");

		tokenManager.verifyToken(token, sysUserDO.getSecret());

		openInterfaceMapResponse.put("userId", String.valueOf(sysUserDO.getId()));
		openInterfaceMapResponse.put("username", username);

		return openInterfaceMapResponse;
	}

	@ApiOperation
	public void revokeToken(@NotEmpty String token) {
	}

}
