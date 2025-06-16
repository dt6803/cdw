package com.cdw_ticket.payment_service.client;

import com.cdw_ticket.payment_service.dto.response.BaseResponse;
import com.cdw_ticket.payment_service.enums.SeatStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "cinema-service",
        url = "${app.services.cinema-service}")
public interface CinemaClient {
    @GetMapping("/seats/updateStatus")
    BaseResponse<Void> updateSeatsStatusByIds(@RequestBody List<String> ids,
                                              @RequestParam SeatStatus status);
}
