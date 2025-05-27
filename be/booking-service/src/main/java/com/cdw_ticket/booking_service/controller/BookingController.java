package com.cdw_ticket.booking_service.controller;

import com.cdw_ticket.booking_service.dto.request.BookingUserRequest;
import com.cdw_ticket.booking_service.dto.response.BaseResponse;
import com.cdw_ticket.booking_service.dto.response.BookingResponse;
import com.cdw_ticket.booking_service.service.BookingService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingController {
    BookingService bookingService;

    @PostMapping("/bookings")
    public BaseResponse<BookingResponse> createBooking(@Valid @RequestBody BookingUserRequest request) {
        return BaseResponse.<BookingResponse>builder()
                .data(bookingService.create(request))
                .build();
    }
}
