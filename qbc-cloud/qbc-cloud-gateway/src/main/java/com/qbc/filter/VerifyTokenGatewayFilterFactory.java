package com.qbc.filter;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qbc.api.ApiRequest;
import com.qbc.api.ApiResponse;
import com.qbc.filter.VerifyTokenGatewayFilterFactory.Config;
import com.qbc.manager.cloud.ApiClientCloudManager;

import io.netty.buffer.ByteBufAllocator;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import reactor.core.publisher.Flux;
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
			ServerHttpRequest request = exchange.getRequest();
			String token = request.getHeaders().getFirst("Authorization");

			ApiRequest apiRequest = getBody(request);
			if (apiRequest == null) {
				error(chain, exchange, ApiResponse.error(HttpStatus.BAD_REQUEST, "Bad Request Body"));
			}

			ApiResponse<Map<String, String>> apiResponse = verifyToken(token, config.getApplicationName(),
					apiRequest.getApiName(), apiRequest.getOperationName());
			if (apiResponse.isOk()) {
				return ok(chain, exchange, apiRequest, apiResponse.getData());
			}

			return error(chain, exchange, apiResponse);
		};
	}

	@SneakyThrows
	private ApiRequest getBody(ServerHttpRequest request) {
		Flux<DataBuffer> body = request.getBody();
		AtomicReference<String> bodyRef = new AtomicReference<>();
		body.subscribe(buffer -> {
			CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
			DataBufferUtils.release(buffer);
			bodyRef.set(charBuffer.toString());
		});
		return objectMapper.readValue(bodyRef.get(), ApiRequest.class);
	}

	private ApiResponse<Map<String, String>> verifyToken(String token, String applicationName, String apiName,
			String operationName) {
		ApiRequest openInterfaceRequest = new ApiRequest("tokenService", "verifyToken");
		openInterfaceRequest.put("token", token);
		openInterfaceRequest.put("applicationName", applicationName);
		openInterfaceRequest.put("apiName", apiName);
		openInterfaceRequest.put("operationName", operationName);
		return apiClientCloudManager.post("qbc-cloud-auth", openInterfaceRequest);
	}

	private Mono<Void> ok(GatewayFilterChain chain, ServerWebExchange exchange, ApiRequest apiRequest,
			Map<String, String> headers) {
		ServerHttpRequest request = exchange.getRequest();
		request = new ServerHttpRequestDecorator(request.mutate().headers(header -> header.setAll(headers)).build()) {

			@Override
			public Flux<DataBuffer> getBody() {
				return Flux.just(getDataBuffer(apiRequest));
			}

		};
		return chain.filter(exchange.mutate().request(request).build());
	}

	@SneakyThrows
	private DataBuffer getDataBuffer(ApiRequest apiRequest) {
		byte[] bytes = objectMapper.writeValueAsBytes(apiRequest);
		NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
		DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
		buffer.write(bytes);
		return buffer;
	}

	@SneakyThrows
	private Mono<Void> error(GatewayFilterChain chain, ServerWebExchange exchange, ApiResponse<?> apiResponse) {
		byte[] bytes = objectMapper.writeValueAsBytes(apiResponse);
		ServerHttpResponse response = exchange.getResponse();
		DataBuffer bodyDataBuffer = response.bufferFactory().wrap(bytes);
		return response.writeWith(Mono.just(bodyDataBuffer));
	}

	@Override
	public List<String> shortcutFieldOrder() {
		return Arrays.asList("applicationName");
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
