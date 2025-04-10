package com.cdw_ticket.authentication_service.repository;

import com.cdw_ticket.authentication_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
