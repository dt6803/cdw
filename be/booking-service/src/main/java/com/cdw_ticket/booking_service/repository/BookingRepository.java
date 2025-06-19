package com.cdw_ticket.booking_service.repository;

import com.cdw_ticket.booking_service.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    List<Booking> findAllByUserId(String userId);
}
