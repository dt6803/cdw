package com.cdw_ticket.profile_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "user_profiles")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "user_id", unique = true)
    int userId;

    @Column(name = "full_name")
    String fullName;

    @Column(name = "dob")
    LocalDate dob;

    @Column(name = "phone_number", unique = true)
    String phoneNumber;

    @Column(name = "citizen_id", unique = true)
    String citizenId;

    @Column(name = "avatar_url")
    String avatarUrl;
}
