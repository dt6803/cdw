package com.cdw_ticket.api_gateway.service.imp;

import com.cdw_ticket.api_gateway.client.AuthenticationClient;
import com.cdw_ticket.api_gateway.dto.request.IntrospectRequest;
import com.cdw_ticket.api_gateway.dto.response.BaseResponse;
import com.cdw_ticket.api_gateway.dto.response.IntrospectResponse;
import com.cdw_ticket.api_gateway.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImp implements AuthenticationService {
    AuthenticationClient authClient;

    @Override
    public Mono<BaseResponse<IntrospectResponse>> introspect(String token) {
        return authClient.introspect(
                IntrospectRequest.builder()
                        .token(token)
                        .build()
        );
    }
}
