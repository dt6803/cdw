package com.cdw_ticket.cinema_service.repository;

import com.cdw_ticket.cinema_service.entity.CinemaBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaBrandRepository extends JpaRepository<CinemaBrand, String> {
}
