package com.qbc.filter;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qbc.api.ApiRequest;
import com.qbc.api.ApiResponse;
import com.qbc.filter.VerifyTokenGatewayFilterFactory.Config;
import com.qbc.manager.cloud.ApiClientCloudManager;

import lombok.Getter;
import lombok.Setter;
import reactor.core.publisher.Mono;

/**
 * Token校验网关过滤器工厂，统一从认证中心验证Token的有效性，并将用户信息设置到请求头部
 *
 * @author Ma
 */
@Setter
public class VerifyTokenGatewayFilterFactory extends AbstractGatewayFilterFactory<Config> {

	private ApiClientCloudManager apiClientCloudManager;

	private ObjectMapper objectMapper;

	public VerifyTokenGatewayFilterFactory() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			ApiResponse<Map<String, String>> openInterfaceResponse = ApiResponse.error(HttpStatus.BAD_REQUEST.value(),
					"token: must not be empty");
			String token = exchange.getRequest().getHeaders().getFirst("Authorization");
			if (StringUtils.isNotEmpty(token)) {
				ApiRequest openInterfaceRequest = new ApiRequest("tokenService", "verifyToken");
				openInterfaceRequest.put("token", token);
				openInterfaceRequest.put("applicationName", config.getApplicationName());
				// TODO
				openInterfaceRequest.put("apiName", "");
				openInterfaceRequest.put("operationName", "");
				openInterfaceResponse = apiClientCloudManager.post("qbc-cloud-auth", openInterfaceRequest);
				if (openInterfaceResponse.isOk()) {
					final Map<String, String> args = openInterfaceResponse.getData();
					ServerHttpRequest request = exchange.getRequest().mutate().headers(header -> header.setAll(args))
							.build();
					return chain.filter(exchange.mutate().request(request).build());
				}

			}

			byte[] bytes = null;
			try {
				bytes = objectMapper.writeValueAsBytes(openInterfaceResponse);
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}
			ServerHttpResponse response = exchange.getResponse();
			DataBuffer bodyDataBuffer = response.bufferFactory().wrap(bytes);
			return response.writeWith(Mono.just(bodyDataBuffer));
		};
	}

	@Getter
	@Setter
	public static class Config {

		/**
		 * 应用名，用于鉴权
		 */
		private String applicationName;

	}

}
