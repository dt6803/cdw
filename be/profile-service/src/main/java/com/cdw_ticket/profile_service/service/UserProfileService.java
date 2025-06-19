package com.cdw_ticket.profile_service.service;

import com.cdw_ticket.profile_service.dto.request.UserProfileRequest;
import com.cdw_ticket.profile_service.dto.request.UserProfileUpdateRequest;
import com.cdw_ticket.profile_service.dto.response.UserProfileResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserProfileService {
    UserProfileResponse create(UserProfileRequest request);
    List<UserProfileResponse> getAll();
    UserProfileResponse getByUserId(String userId);
    UserProfileResponse updateByUserId(String userId, UserProfileUpdateRequest request);
    void deleteByUserId(String userId);
    UserProfileResponse getByEmail(String email);
}
