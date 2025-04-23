package com.cdw_ticket.authentication_service.dto.response;

import com.cdw_ticket.authentication_service.entity.Permission;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleResponse {
    String id;
    String name;
    String createUserId;
    List<Permission> permissions;
}
