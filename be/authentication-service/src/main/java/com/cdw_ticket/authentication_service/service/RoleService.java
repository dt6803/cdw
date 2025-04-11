package com.cdw_ticket.authentication_service.service;

import com.cdw_ticket.authentication_service.dto.request.RoleRequest;
import com.cdw_ticket.authentication_service.dto.response.RoleResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    RoleResponse create(RoleRequest request);
    List<RoleResponse> getAll();
    RoleResponse getById(String id);
    void delete(String id);
}
