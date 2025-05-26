package com.cdw_ticket.cinema_service.repository;

import com.cdw_ticket.cinema_service.entity.CinemaRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CinemaRoomRepository extends JpaRepository<CinemaRoom, String> {
    @Query("SELECT r FROM CinemaRoom r WHERE r.cinema.id = :cinemaId")
    List<CinemaRoom> getRoomsByCinemaId(@Param("cinemaId") String id);
}
