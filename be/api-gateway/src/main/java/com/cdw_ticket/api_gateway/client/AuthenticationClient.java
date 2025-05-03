package com.cdw_ticket.api_gateway.client;

import com.cdw_ticket.api_gateway.dto.request.IntrospectRequest;
import com.cdw_ticket.api_gateway.dto.response.BaseResponse;
import com.cdw_ticket.api_gateway.dto.response.IntrospectResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

public interface AuthenticationClient {
    @PostExchange(url = "/auth/introspect", contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<BaseResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest request);
}
