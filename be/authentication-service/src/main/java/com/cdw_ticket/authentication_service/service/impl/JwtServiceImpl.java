package com.cdw_ticket.authentication_service.service.impl;

import com.cdw_ticket.authentication_service.service.JwtService;
import com.cdw_ticket.authentication_service.service.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class JwtServiceImpl implements JwtService {

    @NonFinal
    @Value("${jwt.secret-key}")
    String SECRET_KEY;

    @NonFinal
    @Value("${jwt.access-expiration}")
    int accessExpiration;

    @NonFinal
    @Value("${jwt.refresh-expiration}")
    int refreshExpiration;

    UserService userService;

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getIssuer);
    }

    @Override
    public String generateAccessToken(UserDetails user) {
        return buildToken(user, new HashMap<>(), accessExpiration);
    }

    @Override
    public String generateRefreshToken(UserDetails user) {
        return buildToken(user, new HashMap<>(), refreshExpiration);
    }

    @Override
    public boolean isValid(String token, UserDetails user) {
        final String username = extractUsername(token);
        return (username.equals(user.getUsername()) && !isExpiredToken(token));
    }

    @Override
    public boolean isValid(String token) {
        return !isExpiredToken(token);
    }

    private String buildToken(UserDetails user, HashMap<String, Object> extraClaims, long expiration) {
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        extraClaims.put("roles", roles);
        String userId = userService.findByUsername(user.getUsername()).getId();
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userId)
                .setIssuer(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private boolean isExpiredToken(String token) {
        try {
            return extractExpireDate(token).before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (JwtException e) {
            return true;
        }
    }

    private Date extractExpireDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimSolver) {
        final Claims claims = extractAllClaim(token);
        return claimSolver.apply(claims);
    }

    private Claims extractAllClaim(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
