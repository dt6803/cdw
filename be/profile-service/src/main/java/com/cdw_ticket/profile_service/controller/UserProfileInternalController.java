package com.cdw_ticket.profile_service.controller;

import com.cdw_ticket.profile_service.dto.request.UserProfileRequest;
import com.cdw_ticket.profile_service.dto.response.BaseResponse;
import com.cdw_ticket.profile_service.dto.response.UserProfileResponse;
import com.cdw_ticket.profile_service.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/internal/users")
public class UserProfileInternalController {
    UserProfileService userProfileService;

    @PostMapping()
    public BaseResponse<UserProfileResponse> create(@RequestBody UserProfileRequest request) {
        return BaseResponse.<UserProfileResponse>builder()
                .data(userProfileService.create(request))
                .build();
    }
}
