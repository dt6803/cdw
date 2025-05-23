package com.cdw_ticket.authentication_service.controller;

import com.cdw_ticket.authentication_service.dto.request.PermissionRequest;
import com.cdw_ticket.authentication_service.dto.response.BaseResponse;
import com.cdw_ticket.authentication_service.dto.response.PermissionResponse;
import com.cdw_ticket.authentication_service.service.PermissionService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/permissions")
public class PermissionController {
    PermissionService permissionService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public BaseResponse<PermissionResponse> create(@Valid @RequestBody PermissionRequest request) {
        return BaseResponse.<PermissionResponse>builder()
                .data(permissionService.create(request))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public BaseResponse<List<PermissionResponse>> getAll() {
        return BaseResponse.<List<PermissionResponse>>builder()
                .data(permissionService.getAll())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public BaseResponse<PermissionResponse> getById(@PathVariable String id) {
        return BaseResponse.<PermissionResponse>builder()
                .data(permissionService.getById(id))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public BaseResponse<PermissionResponse> updateById(
            @PathVariable String id,
            @Valid @RequestBody PermissionRequest request
    ) {
        return BaseResponse.<PermissionResponse>builder()
                .data(permissionService.update(id, request))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public BaseResponse<Void> delete(@PathVariable String id) {
        return BaseResponse.<Void>builder()
                .message("Delete successfully!")
                .build();
    }
}
