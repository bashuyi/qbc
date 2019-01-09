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
			OpenInterfaceResponse<Map<String, String>> openInterfaceResponse = OpenInterfaceResponse
					.error(HttpStatus.BAD_REQUEST.value(), "token: must not be empty");

			String token = exchange.getRequest().getHeaders().getFirst("Authorization");
			if (StringUtils.isNotEmpty(token)) {
				OpenInterfaceRequest openInterfaceRequest = new OpenInterfaceRequest("tokenService", "verifyToken");
				openInterfaceRequest.put("token", token);
				openInterfaceResponse = openInterfaceClientCloudManager.post("qbc-cloud-auth", openInterfaceRequest);
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

}
