package com.cdw_ticket.authentication_service.service.imp;

import com.cdw_ticket.authentication_service.dto.request.RoleRequest;
import com.cdw_ticket.authentication_service.dto.request.RoleUpdateRequest;
import com.cdw_ticket.authentication_service.dto.response.RoleResponse;
import com.cdw_ticket.authentication_service.entity.Permission;
import com.cdw_ticket.authentication_service.exception.AppException;
import com.cdw_ticket.authentication_service.exception.ErrorCode;
import com.cdw_ticket.authentication_service.mapper.RoleMapper;
import com.cdw_ticket.authentication_service.repository.PermissionRepository;
import com.cdw_ticket.authentication_service.repository.RoleRepository;
import com.cdw_ticket.authentication_service.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleServiceImp implements RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;
    @Override
    public RoleResponse create(RoleRequest request) {
        var role = roleMapper.toRole(request);
        Set<Permission> permissions = new HashSet<>();
        request.getPermissions().stream()
                .forEach(p -> permissions.add(permissionRepository.findByName(p)
                        .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED))));
        role.setPermissions(permissions);
        roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    @Override
    public List<RoleResponse> getAll() {
        List<RoleResponse> list = new ArrayList<>();
        roleRepository.findAll().stream()
                .forEach(r -> list.add(roleMapper.toRoleResponse(r)));
        return list;
    }

    @Override
    public RoleResponse getById(String id) {
        var role = roleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        return roleMapper.toRoleResponse(role);
    }

    @Override
    public RoleResponse updateById(String id, RoleUpdateRequest request) {
        var role = roleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        Set<Permission> permissions = new HashSet<>();
        request.getPermissions().stream()
                .forEach(p -> permissions.add(permissionRepository.findByName(p)
                        .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED))));
        roleMapper.update(role, request);
        role.setPermissions(permissions);
        roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    @Override
    public void delete(String id) {
        roleRepository.deleteById(id);
    }
}
