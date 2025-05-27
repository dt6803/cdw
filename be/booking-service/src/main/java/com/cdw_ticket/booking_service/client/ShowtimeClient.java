package com.cdw_ticket.booking_service.client;

import com.cdw_ticket.booking_service.dto.response.BaseResponse;
import com.cdw_ticket.booking_service.dto.response.ShowtimeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "showtime-service",
        url = "${app.services.showtime-service}",
        path = "/showtimes"
)
public interface ShowtimeClient {
    @GetMapping("/{id}")
    BaseResponse<ShowtimeResponse> getById(@PathVariable("id") String id);
}
