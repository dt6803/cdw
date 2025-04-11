package com.cdw_ticket.authentication_service.mapper;

import com.cdw_ticket.authentication_service.dto.request.RoleRequest;
import com.cdw_ticket.authentication_service.dto.response.RoleResponse;
import com.cdw_ticket.authentication_service.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    //    @Mapping(target = "permissions", ignore = true)
    RoleResponse toRoleResponse(Role role);
//    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);
}
