package com.cdw_ticket.api_gateway.client;

import com.cdw_ticket.api_gateway.configuration.GenericHttpClientFactory;
import com.cdw_ticket.api_gateway.service.AuthenticationService;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class HttpClientRegistry {
    GenericHttpClientFactory factory;
    AuthenticationService authService;
    @NonFinal
    @Value("${services.auth}")
    String authUrl;

    @Autowired
    public HttpClientRegistry(GenericHttpClientFactory factory) {
        this.factory = factory;
    }

    @PostConstruct
    public void init() {
        this.authService = factory.createClient(AuthenticationService.class, authUrl);
    }

}
