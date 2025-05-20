package com.cdw_ticket.showtime_service.repository;

import com.cdw_ticket.showtime_service.entity.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, String> {

}
