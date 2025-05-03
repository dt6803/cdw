package com.cdw_ticket.api_gateway.service;

import com.cdw_ticket.api_gateway.dto.response.BaseResponse;
import com.cdw_ticket.api_gateway.dto.response.IntrospectResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface AuthenticationService {
    Mono<BaseResponse<IntrospectResponse>> introspect(String token);
}
