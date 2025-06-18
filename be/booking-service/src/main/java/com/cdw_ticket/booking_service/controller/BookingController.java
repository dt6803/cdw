package com.cdw_ticket.booking_service.controller;

import com.cdw_ticket.booking_service.dto.request.BookingUserRequest;
import com.cdw_ticket.booking_service.dto.response.BaseResponse;
import com.cdw_ticket.booking_service.dto.response.BookingResponse;
import com.cdw_ticket.booking_service.service.BookingService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping ("/bookings/markedBooked/{id}")
    public BaseResponse<BookingResponse> markBooked(@PathVariable String id) {
        return BaseResponse.<BookingResponse>builder()
                .message("Order has been marked as booked successfully.")
                .data(bookingService.markBooked(id))
                .build();
    }

    @GetMapping("/bookings/{id}/status")
    public BaseResponse<BookingResponse> checkStatus(@PathVariable String id) {
        return BaseResponse.<BookingResponse>builder()
                .data(bookingService.checkStatus(id))
                .build();
    }

    @GetMapping("/bookings/{id}")
    public BaseResponse<BookingResponse> getById(@PathVariable String id) {
        return BaseResponse.<BookingResponse>builder()
                .data(bookingService.getById(id))
                .build();
    }

    @GetMapping("/bookings")
    public BaseResponse<List<BookingResponse>> getAll() {
        return BaseResponse.<List<BookingResponse>>builder()
                .data(bookingService.getAll())
                .build();
    }

    @GetMapping("/bookings/{id}/seats")
    public BaseResponse<List<String>> getSeatByBookingId(@PathVariable String id) {
        return BaseResponse.<List<String>>builder()
                .data(bookingService.getSeatsByBookingId(id))
                .build();
    }

    @GetMapping("/bookings/user/{userId}")
    public BaseResponse<List<BookingResponse>> getAllBookingByUserId(@PathVariable String userId) {
        return BaseResponse.<List<BookingResponse>>builder()
                .data(bookingService.getAllByUserId(userId))
                .build();
    }
}
