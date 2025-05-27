package com.cdw_ticket.booking_service.client;

import com.cdw_ticket.booking_service.dto.response.BaseResponse;
import com.cdw_ticket.booking_service.dto.response.SeatResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(
        name = "cinema-service",
        url = "${app.services.cinema-service}")
public interface CinemaClient {
    @GetMapping("/seats")
    BaseResponse<List<SeatResponse>> getAvailableSeatByIds(List<String> seatIds);
}
