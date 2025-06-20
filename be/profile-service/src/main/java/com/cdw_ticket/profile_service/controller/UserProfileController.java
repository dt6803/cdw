package com.cdw_ticket.profile_service.controller;

import com.cdw_ticket.profile_service.dto.request.UserProfileRequest;
import com.cdw_ticket.profile_service.dto.request.UserProfileUpdateRequest;
import com.cdw_ticket.profile_service.dto.response.BaseResponse;
import com.cdw_ticket.profile_service.dto.response.UserProfileResponse;
import com.cdw_ticket.profile_service.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/users")
public class UserProfileController {
    UserProfileService userProfileService;

    @GetMapping("/{userId}")
    public BaseResponse<UserProfileResponse> getByUserId(@PathVariable String userId) {
        return BaseResponse.<UserProfileResponse>builder()
                .data(userProfileService.getByUserId(userId))
                .build();
    }

    @GetMapping
    public BaseResponse<List<UserProfileResponse>> getAll() {
        return BaseResponse.<List<UserProfileResponse>>builder()
                .data(userProfileService.getAll())
                .build();
    }

    @PutMapping("/{userId}")
    public BaseResponse<UserProfileResponse> updateByUserId(
            @PathVariable String userId,
            @Valid @RequestBody UserProfileUpdateRequest request
    ) {
        return BaseResponse.<UserProfileResponse>builder()
                .data(userProfileService.updateByUserId(userId, request))
                .build();
    }

    @DeleteMapping("/{userId}")
    public BaseResponse<String> deleteByUserId(@PathVariable String userId) {
        userProfileService.deleteByUserId(userId);
        return BaseResponse.<String>builder()
                .data(new String("Delete successfully!"))
                .build();
    }

    @GetMapping("/findEmail/{email}")
    public BaseResponse<UserProfileResponse> getByEmail(@PathVariable String email) {
        return BaseResponse.<UserProfileResponse>builder()
                .data(userProfileService.getByEmail(email))
                .build();
    }
}
