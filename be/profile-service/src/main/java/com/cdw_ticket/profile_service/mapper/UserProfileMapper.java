package com.cdw_ticket.profile_service.mapper;

import com.cdw_ticket.profile_service.dto.request.UserProfileRequest;
import com.cdw_ticket.profile_service.dto.request.UserProfileUpdateRequest;
import com.cdw_ticket.profile_service.dto.response.UserProfileResponse;
import com.cdw_ticket.profile_service.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(UserProfileRequest request);
    UserProfileResponse toUserProfileResponse(UserProfile userProfile);
    void update(@MappingTarget UserProfile profile, UserProfileUpdateRequest request);
}
