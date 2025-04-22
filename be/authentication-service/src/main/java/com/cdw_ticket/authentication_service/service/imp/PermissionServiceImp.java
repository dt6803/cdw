package com.cdw_ticket.authentication_service.service.imp;

import com.cdw_ticket.authentication_service.dto.request.PermissionRequest;
import com.cdw_ticket.authentication_service.dto.response.PermissionResponse;
import com.cdw_ticket.authentication_service.exception.AppException;
import com.cdw_ticket.authentication_service.exception.ErrorCode;
import com.cdw_ticket.authentication_service.mapper.PermissionMapper;
import com.cdw_ticket.authentication_service.repository.PermissionRepository;
import com.cdw_ticket.authentication_service.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionServiceImp implements PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    @Override
    public PermissionResponse create(PermissionRequest request) {
        var permission = permissionMapper.toPermission(request);
        return permissionMapper.toPermissionResponse(permissionRepository.save(permission));
    }

    @Override
    public List<PermissionResponse> getAll() {
        List<PermissionResponse> list = new ArrayList<>();
        permissionRepository.findAll()
                .stream().forEach(p -> list.add(permissionMapper.toPermissionResponse(p)));
        return list;
    }

    @Override
    public PermissionResponse getById(String id) {
        var permission = permissionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));
        return permissionMapper.toPermissionResponse(permission);
    }

    @Override
    public PermissionResponse update(String id, PermissionRequest request) {
        return null;
    }

    @Override
    public void delete(String id) {
        permissionRepository.deleteById(id);
    }
}
