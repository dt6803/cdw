package com.cdw_ticket.authentication_service.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface JwtService {
    String extractUsername(String token);
    String generateAccessToken(UserDetails user);
    String generateRefreshToken(UserDetails user);
    boolean isValid(String token, UserDetails user);
    boolean isValid(String token);

}
