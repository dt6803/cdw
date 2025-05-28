package com.cdw_ticket.showtime_service.client;

import com.cdw_ticket.showtime_service.dto.response.BaseResponse;
import com.cdw_ticket.showtime_service.dto.response.MovieResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "movie-service",
        url = "${app.services.movie-service}"
)
public interface MovieClient {
    @GetMapping("/movies/{id}")
    BaseResponse<MovieResponse> getMovieById(@PathVariable String id);
}
