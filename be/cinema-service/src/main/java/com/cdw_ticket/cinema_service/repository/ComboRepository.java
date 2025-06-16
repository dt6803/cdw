package com.cdw_ticket.cinema_service.repository;

import com.cdw_ticket.cinema_service.entity.Combo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComboRepository extends JpaRepository<Combo, String> {
}
