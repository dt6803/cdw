package com.cdw_ticket.payment_service.client;

import com.cdw_ticket.payment_service.dto.response.BaseResponse;
import com.cdw_ticket.payment_service.dto.response.BookingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(
        name = "booking-service",
        url = "${app.services.booking-service}")
public interface BookingClient {
    @PostMapping("/bookings/markedBooked/{id}")
    BaseResponse<BookingResponse> markBooked(@PathVariable String id);

    @GetMapping("/bookings/{id}/seats")
    BaseResponse<List<String>> getSeatByBookingId(@PathVariable String id);
}
