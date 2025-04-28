package com.cdw_ticket.authentication_service.service;

import com.cdw_ticket.authentication_service.entity.Token;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {
    Token getByUsername(String username);
    int save(Token token);
    void delete(String username);
}
