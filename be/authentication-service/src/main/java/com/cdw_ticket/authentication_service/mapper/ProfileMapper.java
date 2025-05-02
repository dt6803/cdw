package com.cdw_ticket.authentication_service.mapper;

import com.cdw_ticket.authentication_service.dto.request.RegisterRequest;
import com.cdw_ticket.authentication_service.dto.request.UserCreationRequest;
import com.cdw_ticket.authentication_service.dto.request.UserProfileRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    UserProfileRequest toProfile(RegisterRequest request);
}
