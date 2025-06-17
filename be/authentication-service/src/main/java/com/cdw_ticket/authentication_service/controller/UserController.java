package com.cdw_ticket.authentication_service.controller;

import com.cdw_ticket.authentication_service.dto.request.RegisterRequest;
import com.cdw_ticket.authentication_service.dto.request.UserCreationRequest;
import com.cdw_ticket.authentication_service.dto.request.UserUpdateRequest;
import com.cdw_ticket.authentication_service.dto.request.UserUpdateRoleRequest;
import com.cdw_ticket.authentication_service.dto.response.BaseResponse;
import com.cdw_ticket.authentication_service.dto.response.UserResponse;
import com.cdw_ticket.authentication_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/users")
@Slf4j
public class UserController {
    UserService userService;

    @PostMapping("/registration")
    public BaseResponse<UserResponse> create(@Valid @RequestBody RegisterRequest request) {
        return BaseResponse.<UserResponse>builder()
                .data(userService.create(request))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public BaseResponse<List<UserResponse>> getAll() {
        return BaseResponse.<List<UserResponse>>builder()
                .data(userService.getAll())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public BaseResponse<UserResponse> getById(@PathVariable String id) {
        return BaseResponse.<UserResponse>builder()
                .data(userService.getById(id))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public BaseResponse<UserResponse> updateById(@PathVariable String id,
                                                 @Valid @RequestBody UserUpdateRequest request) {
        return BaseResponse.<UserResponse>builder()
                .data(userService.update(id, request))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateRole/{id}")
    public BaseResponse<UserResponse> updateRoleById(@PathVariable String id,
                                                     @Valid @RequestBody UserUpdateRoleRequest request) {
        return BaseResponse.<UserResponse>builder()
                .data(userService.update(id, request))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteById(@PathVariable String id) {
        return BaseResponse.<Void>builder()
                .message("Delete Successfully!")
                .build();
    }

    @GetMapping("/me")
    public BaseResponse<UserResponse> getMyInfo() {
        return BaseResponse.<UserResponse>builder()
                .data(userService.getMyInfo("123"))
                .build();
    }
}
