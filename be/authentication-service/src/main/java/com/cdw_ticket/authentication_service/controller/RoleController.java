package com.cdw_ticket.authentication_service.controller;

import com.cdw_ticket.authentication_service.dto.request.RoleRequest;
import com.cdw_ticket.authentication_service.dto.response.BaseResponse;
import com.cdw_ticket.authentication_service.dto.response.RoleResponse;
import com.cdw_ticket.authentication_service.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/roles")
@Tag(name = "Role Controller")
public class RoleController {
    RoleService roleService;

    @PostMapping
    public BaseResponse<RoleResponse> create(@Valid @RequestBody RoleRequest request) {
        return BaseResponse.<RoleResponse>builder()
                .data(roleService.create(request))
                .build();
    }

    @GetMapping
    public BaseResponse<List<RoleResponse>> getAll() {
        return BaseResponse.<List<RoleResponse>>builder()
                .data(roleService.getAll())
                .build();
    }

    @GetMapping("/{id}")
    public BaseResponse<RoleResponse> getById(@PathVariable String id) {
        return BaseResponse.<RoleResponse>builder()
                .data(roleService.getById(id))
                .build();
    }

    @DeleteMapping("/{id}")
    public BaseResponse<String> delete(@PathVariable String id) {
        roleService.delete(id);
        return BaseResponse.<String>builder()
                .data(new String("Delete successfully!"))
                .build();
    }
}
