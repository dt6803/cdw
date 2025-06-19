package com.cdw_ticket.authentication_service.service;

import com.cdw_ticket.authentication_service.dto.request.RegisterRequest;
import com.cdw_ticket.authentication_service.dto.request.UserCreationRequest;
import com.cdw_ticket.authentication_service.dto.request.UserUpdateRequest;
import com.cdw_ticket.authentication_service.dto.request.UserUpdateRoleRequest;
import com.cdw_ticket.authentication_service.dto.response.UserResponse;
import com.cdw_ticket.authentication_service.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

public interface UserService {
    UserResponse create(RegisterRequest request);
    List<UserResponse> getAll();
    UserResponse getById(String id);
    UserResponse update(String id, UserUpdateRequest request);
    UserResponse update(String id, UserUpdateRoleRequest request);
    void delete(String id);
    UserDetailsService userDetailsService();
    User findByUsername(String username);
    Set<String> getRolesById(String id);
    UserResponse getMyInfo(String token);
}
