package com.cdw_ticket.booking_service.service;

import com.cdw_ticket.booking_service.dto.request.BookingUserRequest;
import com.cdw_ticket.booking_service.dto.response.BookingResponse;
import org.springframework.stereotype.Service;

@Service
public interface BookingService {
    BookingResponse create(BookingUserRequest request);
}
