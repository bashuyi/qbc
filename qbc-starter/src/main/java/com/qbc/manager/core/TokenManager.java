package com.qbc.manager.core;

import java.util.Date;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class TokenManager {

	@Autowired
	private TokenProperties tokenProperties;

	public String createToken(String audience, String secret, Integer expiresAfterHours) {
		// 过期时间
		Date expiresAt = DateUtils.addHours(new Date(),
				ObjectUtils.defaultIfNull(expiresAfterHours, tokenProperties.getExpiresAfterHours()));
		// 签名算法
		Algorithm algorithm = Algorithm.HMAC256(secret);
		// 生成Token
		return JWT.create().withIssuer(tokenProperties.getIssuer()).withExpiresAt(expiresAt).withAudience(audience)
				.sign(algorithm);
	}

	public String createToken(String audience, String secret) {
		return createToken(audience, secret, null);
	}

	public String getAudience(String token) {
		DecodedJWT decodedJWT = JWT.decode(token);
		return CollectionUtils.lastElement(decodedJWT.getAudience());
	}

	public DecodedJWT verifyToken(String token, String secret) {
		// 签名算法
		Algorithm algorithm = Algorithm.HMAC256(secret);
		// 验证签名
		return JWT.require(algorithm).withIssuer(tokenProperties.getIssuer()).build().verify(token);
	}

}
