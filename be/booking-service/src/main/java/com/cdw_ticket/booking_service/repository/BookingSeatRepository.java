package com.cdw_ticket.booking_service.repository;

import com.cdw_ticket.booking_service.entity.BookingSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingSeatRepository extends JpaRepository<BookingSeat, String> {
    @Query("SELECT bs FROM BookingSeat bs WHERE bs.booking.id = :bookingId")
    List<BookingSeat> findByBookingId(@Param("bookingId") String bookingId);
}
