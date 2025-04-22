package com.cdw_ticket.authentication_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "permissions")
public class Permission extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "create_user_id", nullable = false)
    String createUserId;

    @Column(name = "description", nullable = false)
    String description;

//    @ManyToMany(mappedBy = "permissions")
//    Set<Role> roles = new HashSet<>();
}
