package com.cdw_ticket.api_gateway.configuration;

import com.cdw_ticket.api_gateway.dto.response.BaseResponse;
import com.cdw_ticket.api_gateway.service.AuthenticationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationFilter implements GlobalFilter, Ordered {
    AuthenticationService authService;
    ObjectMapper objMapper;
    @NonFinal
    String[] publicEndpoint = {
            "/authentication/auth/.*",
            "/authentication/users/registration",
            "/movie/.*",
            "/profile/.*",
            "/cinema/.*",
            "/showtime/.*",
            "/booking/.*",
            "/payment/.*",
            "/notification/.*"
    };
    @NonFinal
    @Value(("${app.api-prefix}"))
    String appPrefix;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Enter authentication...");
        if(isPublicEndpoint(exchange.getRequest()))
            return chain.filter(exchange);
        // Get token from authorization header
        List<String> authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (CollectionUtils.isEmpty(authHeader))
            return unauthenticated(exchange.getResponse());
        String token = authHeader.get(0).replace("Bearer ", "");
        log.info("Token: {}", token);
        return authService.introspect(token).flatMap(introspectResponse -> {
            if (introspectResponse.getData().isValid())
                return chain.filter(exchange);
            else
                return unauthenticated(exchange.getResponse());

        }).onErrorResume(throwable -> exception(exchange.getResponse()));
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private Mono<Void> unauthenticated(ServerHttpResponse response) {
        BaseResponse<?> baseResponse = BaseResponse.builder()
                .status("Error")
                .code(1401)
                .message("Unauthenticated")
                .build();

        String body = null;
        try {
            body = objMapper.writeValueAsString(baseResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return response.writeWith(
                Mono.just(response.bufferFactory().wrap(body.getBytes()))
        );
    }

    private Mono<Void> exception(ServerHttpResponse response) {
        BaseResponse<?> baseResponse = BaseResponse.builder()
                .status("Error")
                .code(1401)
                .message("Failed")
                .build();

        String body = null;
        try {
            body = objMapper.writeValueAsString(baseResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return response.writeWith(
                Mono.just(response.bufferFactory().wrap(body.getBytes()))
        );
    }

    private boolean isPublicEndpoint(ServerHttpRequest request) {
        return Arrays.stream(publicEndpoint)
                .anyMatch(s -> request.getURI().getPath().matches(appPrefix + s));
    }
}