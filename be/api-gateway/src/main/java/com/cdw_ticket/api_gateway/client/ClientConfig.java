package com.cdw_ticket.api_gateway.client;

import com.cdw_ticket.api_gateway.configuration.GenericHttpClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {
    @Value("${services.auth}")
    private String authUrl;

    @Bean
    public AuthenticationClient authenticationClient(GenericHttpClientFactory factory) {
        return factory.createClient(AuthenticationClient.class, authUrl);
    }
}
