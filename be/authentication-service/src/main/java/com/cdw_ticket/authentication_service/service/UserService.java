package com.cdw_ticket.authentication_service.service;

import com.cdw_ticket.authentication_service.dto.request.UserCreationRequest;
import com.cdw_ticket.authentication_service.dto.request.UserUpdateRequest;
import com.cdw_ticket.authentication_service.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse create(UserCreationRequest request);
    List<UserResponse> getAll();
    UserResponse getById(String id);
    UserResponse update(String id, UserUpdateRequest request);
    void delete(String id);
}
