package com.cdw_ticket.authentication_service.service.impl;

import com.cdw_ticket.authentication_service.client.NotificationClient;
import com.cdw_ticket.authentication_service.dto.request.*;
import com.cdw_ticket.authentication_service.dto.response.AuthenticationResponse;
import com.cdw_ticket.authentication_service.dto.response.IntrospectResponse;
import com.cdw_ticket.authentication_service.entity.Recipient;
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

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    AuthenticationManager authenticationManager;
    JwtService jwtService;
    UserService userService;
    TokenService tokenService;
    NotificationClient notificationClient;

    @Override
    public AuthenticationResponse authenticate(LogInRequest request) {
        try {
            User user = userService.findByUsername(request.getUsername());
            Set<String> roles = userService.getRolesById(user.getId());
            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
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

        } catch (AppException ex) {
            if (ex.getErrorCode() == ErrorCode.USER_NOT_EXISTED) {
                return AuthenticationResponse.builder()
                        .message("Login failed: Incorrect username or password")
                        .status("fail")
                        .build();
            }
            throw ex; // các lỗi khác vẫn cho nổ tiếp
        }
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
    public void forgotPassword(ForgotPasswordRequest request) {
        //String newPass = generateRandomPassword(6);
        String newPass = "55555";
        userService.updatePassword(request.getUserId(), newPass);

        var recipient = Recipient.builder()
                .name("user")
                .email(request.getEmail())
                .build();

        Map<String, Object> data = new HashMap<>();
        data.put("fullname", request.getUserId());
        data.put("newPassword", newPass);
        data.put("loginUrl", "#");

        notificationClient.sendMail(SendMailRequest.builder()
                        .to(recipient)
                        .subject("T CINEMA: KHÔI PHỤC MẬT KHẨU")
                        .htmlContent("reset-password")
                        .data(data)
                .build());

    }

    private String generateRandomPassword(int length) {
        int min = (int) Math.pow(10, length - 1); // ví dụ: 100000
        int max = (int) Math.pow(10, length) - 1; // ví dụ: 999999
        int randomNum = new SecureRandom().nextInt((max - min) + 1) + min;
        return String.valueOf(randomNum);
    }


    @Override
    public IntrospectResponse introspectToken(IntrospectRequest request) {
        return IntrospectResponse.builder()
                .isValid(jwtService.isValid(request.getToken()))
                .build();
    }
}
