package com.cdw_ticket.authentication_service.mapper;

import com.cdw_ticket.authentication_service.dto.request.UserCreationRequest;
import com.cdw_ticket.authentication_service.dto.request.UserUpdateRequest;
import com.cdw_ticket.authentication_service.dto.request.UserUpdateRoleRequest;
import com.cdw_ticket.authentication_service.dto.response.UserResponse;
import com.cdw_ticket.authentication_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    UserResponse toUserResponse(User user);
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRoleRequest request);
}
