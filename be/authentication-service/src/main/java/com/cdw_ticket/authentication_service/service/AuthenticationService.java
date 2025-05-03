package com.cdw_ticket.authentication_service.service;

import com.cdw_ticket.authentication_service.dto.request.IntrospectRequest;
import com.cdw_ticket.authentication_service.dto.request.LogInRequest;
import com.cdw_ticket.authentication_service.dto.request.RefreshRequest;
import com.cdw_ticket.authentication_service.dto.response.AuthenticationResponse;
import com.cdw_ticket.authentication_service.dto.response.IntrospectResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    AuthenticationResponse authenticate(LogInRequest request);
    AuthenticationResponse refresh(RefreshRequest request);
    IntrospectResponse introspectToken(IntrospectRequest request);
    void delete(HttpServletRequest request);
}
