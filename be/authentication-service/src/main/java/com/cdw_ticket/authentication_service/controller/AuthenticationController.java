package com.cdw_ticket.authentication_service.controller;

import com.cdw_ticket.authentication_service.dto.request.IntrospectRequest;
import com.cdw_ticket.authentication_service.dto.request.LogInRequest;
import com.cdw_ticket.authentication_service.dto.request.RefreshRequest;
import com.cdw_ticket.authentication_service.dto.response.AuthenticationResponse;
import com.cdw_ticket.authentication_service.dto.response.BaseResponse;
import com.cdw_ticket.authentication_service.dto.response.IntrospectResponse;
import com.cdw_ticket.authentication_service.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/auth")
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public BaseResponse<AuthenticationResponse> login(@RequestBody LogInRequest request) {
        return BaseResponse.<AuthenticationResponse>builder()
                .data(authenticationService.authenticate(request))
                .build();
    }

    @PostMapping("/refresh")
    public BaseResponse<AuthenticationResponse> refresh(@RequestBody RefreshRequest request) {
        return BaseResponse.<AuthenticationResponse>builder()
                .data(authenticationService.refresh(request))
                .build();
    }

    @PostMapping("/introspect")
    public BaseResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) {
        return BaseResponse.<IntrospectResponse>builder()
                .data(authenticationService.introspectToken(request))
                .build();
    }

    @PostMapping("/logout")
    public BaseResponse<String> logout(HttpServletRequest request) {
        authenticationService.delete(request);
        return BaseResponse.<String>builder()
                .data(new String("Delete successfully!"))
                .build();
    }
}
