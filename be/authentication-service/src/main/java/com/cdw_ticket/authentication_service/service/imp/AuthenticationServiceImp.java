package com.cdw_ticket.authentication_service.service.imp;

import com.cdw_ticket.authentication_service.dto.request.IntrospectRequest;
import com.cdw_ticket.authentication_service.dto.request.LogInRequest;
import com.cdw_ticket.authentication_service.dto.request.RefreshRequest;
import com.cdw_ticket.authentication_service.dto.response.AuthenticationResponse;
import com.cdw_ticket.authentication_service.dto.response.IntrospectResponse;
import com.cdw_ticket.authentication_service.entity.Token;
import com.cdw_ticket.authentication_service.entity.User;
import com.cdw_ticket.authentication_service.exception.AppException;
import com.cdw_ticket.authentication_service.exception.ErrorCode;
import com.cdw_ticket.authentication_service.service.AuthenticationService;
import com.cdw_ticket.authentication_service.service.JwtService;
import com.cdw_ticket.authentication_service.service.TokenService;
import com.cdw_ticket.authentication_service.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationServiceImp implements AuthenticationService {
    AuthenticationManager authenticationManager;
    JwtService jwtService;
    UserService userService;
    TokenService tokenService;

    @Override
    public AuthenticationResponse authenticate(LogInRequest request) {
        User user = userService.findByUsername(request.getUsername());
        Set<String> roles = userService.getRolesById(user.getId());
        List<SimpleGrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new).toList();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword(),
                        authorities
                )
        );

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        tokenService.save(Token.builder()
                .username(user.getUsername())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build());

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthenticationResponse refresh(RefreshRequest request) {
        String token = request.getToken();
        final String username = jwtService.extractUsername(token);

        if (StringUtils.isBlank(username))
            throw new AppException(ErrorCode.TOKEN_NOT_FOUND);

        var user = userService.findByUsername(username);
        if (!jwtService.isValid(token, user))
            throw new AppException(ErrorCode.REFRESH_TOKEN_INVALID);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        tokenService.save(Token.builder()
                .username(user.getUsername())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build());

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void delete(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (StringUtils.isBlank(authHeader) || !authHeader.startsWith("Bearer ")) {
            throw new AppException(ErrorCode.TOKEN_NOT_FOUND);
        }
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);
        tokenService.delete(username);
    }

    @Override
    public IntrospectResponse introspectToken(IntrospectRequest request) {
        return IntrospectResponse.builder()
                .isValid(jwtService.isValid(request.getToken()))
                .build();
    }
}
