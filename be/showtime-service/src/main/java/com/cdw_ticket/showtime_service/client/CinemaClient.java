package com.cdw_ticket.showtime_service.client;

import com.cdw_ticket.showtime_service.dto.response.BaseResponse;
import com.cdw_ticket.showtime_service.dto.response.CinemaSimpleResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "cinema-service",
        url = "${app.services.cinema-service}")
public interface CinemaClient {
    @GetMapping("/cinemas/info/{id}")
    BaseResponse<CinemaSimpleResponse> getSimpleInfo(@PathVariable String id);
}
