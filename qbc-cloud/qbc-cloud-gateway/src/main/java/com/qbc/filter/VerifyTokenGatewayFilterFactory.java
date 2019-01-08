package com.qbc.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qbc.manager.cloud.OpenInterfaceClientCloudManager;
import com.qbc.openinterface.OpenInterfaceRequest;
import com.qbc.openinterface.OpenInterfaceResponse;

import lombok.Setter;
import reactor.core.publisher.Mono;

@Setter
public class VerifyTokenGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

	private OpenInterfaceClientCloudManager openInterfaceClientCloudManager;

	private ObjectMapper objectMapper;

	@Override
	public GatewayFilter apply(Object config) {
		return (exchange, chain) -> {
			OpenInterfaceResponse<Void> openInterfaceResponse = OpenInterfaceResponse
					.error(HttpStatus.BAD_REQUEST.value(), "token: must not be empty");

			String token = exchange.getRequest().getHeaders().getFirst("Authorization");
			if (StringUtils.isNotEmpty(token)) {
				OpenInterfaceRequest request = new OpenInterfaceRequest("tokenService", "verifyToken");
				request.put("token", token);
				openInterfaceResponse = openInterfaceClientCloudManager.post("qbc-cloud-auth", request);
				if (openInterfaceResponse.isOk()) {
					return chain.filter(exchange);
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

}
