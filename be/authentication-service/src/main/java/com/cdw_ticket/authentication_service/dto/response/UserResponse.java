package com.cdw_ticket.authentication_service.dto.response;

import com.cdw_ticket.authentication_service.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    String email;
    Set<Role> roles;
}
