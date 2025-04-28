package com.cdw_ticket.authentication_service.service.imp;

import com.cdw_ticket.authentication_service.entity.Token;
import com.cdw_ticket.authentication_service.exception.AppException;
import com.cdw_ticket.authentication_service.exception.ErrorCode;
import com.cdw_ticket.authentication_service.repository.TokenRepository;
import com.cdw_ticket.authentication_service.service.TokenService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TokenServiceImp implements TokenService {
    TokenRepository tokenRepository;
    @Override
    public Token getByUsername(String username) {
        return tokenRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.TOKEN_NOT_FOUND));
    }

    @Override
    public int save(Token token) {
        Optional<Token> optional = tokenRepository.findByUsername(token.getUsername());
        if (optional.isEmpty()) {
            tokenRepository.save(token);
            return token.getId();
        }
        Token t = optional.get();
        t.setAccessToken(token.getAccessToken());
        t.setRefreshToken(token.getRefreshToken());
        tokenRepository.save(t);
        return t.getId();
    }

    @Override
    public void delete(String username) {
        Token token = getByUsername(username);
        tokenRepository.delete(token);
    }
}
