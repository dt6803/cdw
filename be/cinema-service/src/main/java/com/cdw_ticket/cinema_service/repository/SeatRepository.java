package com.cdw_ticket.cinema_service.repository;

import com.cdw_ticket.cinema_service.entity.Seat;
import com.cdw_ticket.cinema_service.enums.SeatStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, String> {
    @Query("SELECT s FROM Seat s WHERE s.room.id = :roomId")
    List<Seat> getAllSeatsByRoomId(@Param("roomId") String roomId);
    @Modifying
    @Transactional
    @Query("DELETE FROM Seat s WHERE s.room.id = :roomId")
    void deleteAllByRoomId(@Param("roomId") String roomId);

    List<Seat> findByIdInAndStatus(List<String> ids, SeatStatus status);

    @Modifying
    @Transactional
    @Query("UPDATE Seat s SET s.status = :status WHERE s.id IN :seatIds")
    int updateStatusBySeatIds(@Param("seatIds") List<String> seatIds, @Param("status") SeatStatus status);
}
