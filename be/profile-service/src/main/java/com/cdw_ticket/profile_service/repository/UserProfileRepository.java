package com.cdw_ticket.profile_service.repository;

import com.cdw_ticket.profile_service.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    Optional<UserProfile> findByUserId(String userId);
    void deleteByUserId(String userId);
}
