package com.cdw_ticket.payment_service.client;

import com.cdw_ticket.payment_service.dto.response.BaseResponse;
import com.cdw_ticket.payment_service.dto.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "profile-service",
        url = "${app.services.profile-service}")
public interface ProfileClient {
    @GetMapping("/{userId}")
    BaseResponse<UserProfileResponse> getByUserId(@PathVariable String userId);
}
