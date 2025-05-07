package com.cdw_ticket.authentication_service.client;

import com.cdw_ticket.authentication_service.configuration.AuthenticationRequestInterceptor;
import com.cdw_ticket.authentication_service.dto.request.UserProfileRequest;
import com.cdw_ticket.authentication_service.dto.request.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "profile-service",
        url = "${app.services.profile}",
        configuration = {AuthenticationRequestInterceptor.class}
)
public interface ProfileClient {
    @PostMapping(value = "/internal/users", produces = MediaType.APPLICATION_JSON_VALUE)
    UserProfileResponse createProfile(UserProfileRequest request);
}
