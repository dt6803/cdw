package com.cdw_ticket.authentication_service.mapper;

import com.cdw_ticket.authentication_service.dto.request.PermissionRequest;
import com.cdw_ticket.authentication_service.dto.response.PermissionResponse;
import com.cdw_ticket.authentication_service.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
