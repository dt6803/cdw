package com.cdw_ticket.booking_service.service;

import com.cdw_ticket.booking_service.dto.request.BookingUserRequest;
import com.cdw_ticket.booking_service.dto.response.BookingResponse;
import com.cdw_ticket.booking_service.entity.BookingSeat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingService {
    BookingResponse create(BookingUserRequest request);
    BookingResponse markBooked(String id);
    BookingResponse checkStatus(String id);
    BookingResponse getById(String id);
    List<BookingResponse> getAll();
    List<String> getSeatsByBookingId(String id);
    List<BookingResponse> getAllByUserId(String userId);
}
