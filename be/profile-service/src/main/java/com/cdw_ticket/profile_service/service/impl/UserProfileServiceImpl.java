package com.cdw_ticket.profile_service.service.impl;

import com.cdw_ticket.profile_service.dto.request.UserProfileRequest;
import com.cdw_ticket.profile_service.dto.request.UserProfileUpdateRequest;
import com.cdw_ticket.profile_service.dto.response.UserProfileResponse;
import com.cdw_ticket.profile_service.exception.AppException;
import com.cdw_ticket.profile_service.exception.ErrorCode;
import com.cdw_ticket.profile_service.mapper.UserProfileMapper;
import com.cdw_ticket.profile_service.repository.UserProfileRepository;
import com.cdw_ticket.profile_service.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileServiceImpl implements UserProfileService {
    UserProfileRepository userProfileRepository;
    UserProfileMapper userProfileMapper;

    @Override
    public UserProfileResponse create(UserProfileRequest request) {
        var profile = userProfileMapper.toUserProfile(request);
        userProfileRepository.save(profile);
        return userProfileMapper.toUserProfileResponse(profile);
    }

    @Override
    public List<UserProfileResponse> getAll() {
        return userProfileRepository.findAll()
                .stream()
                .map(userProfileMapper::toUserProfileResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserProfileResponse getByUserId(String userId) {
        var profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.PROFILE_NOT_FOUND));
        return userProfileMapper.toUserProfileResponse(profile);
    }

    @Override
    public UserProfileResponse updateByUserId(String userId, UserProfileUpdateRequest request) {
        var profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.PROFILE_NOT_FOUND));
        userProfileMapper.update(profile, request);
        userProfileRepository.save(profile);
        return userProfileMapper.toUserProfileResponse(profile);
    }

    @Override
    public void deleteByUserId(String userId) {
        userProfileRepository.deleteByUserId(userId);
    }

    @Override
    public UserProfileResponse getByEmail(String email) {
        var profile = userProfileRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.PROFILE_NOT_FOUND));
        return userProfileMapper.toUserProfileResponse(profile);
    }
}
