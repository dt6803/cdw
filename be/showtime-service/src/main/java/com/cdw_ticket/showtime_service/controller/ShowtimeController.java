package com.cdw_ticket.showtime_service.controller;

import com.cdw_ticket.showtime_service.dto.request.ShowtimeRequest;
import com.cdw_ticket.showtime_service.dto.response.BaseResponse;
import com.cdw_ticket.showtime_service.dto.response.ShowtimeResponse;
import com.cdw_ticket.showtime_service.service.ShowtimeService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/showtimes")
@Slf4j
public class ShowtimeController {
    ShowtimeService showtimeService;

    @PostMapping
    public BaseResponse<ShowtimeResponse> create(@Valid @RequestBody ShowtimeRequest request) {
        return BaseResponse.<ShowtimeResponse>builder()
                .data(showtimeService.create(request))
                .build();
    }

    @GetMapping
    public BaseResponse<List<ShowtimeResponse>> getAll() {
        return BaseResponse.<List<ShowtimeResponse>>builder()
                .data(showtimeService.getAll())
                .build();
    }

    @GetMapping("/{id}")
    public BaseResponse<ShowtimeResponse> getById(@PathVariable String id) {
        return BaseResponse.<ShowtimeResponse>builder()
                .data(showtimeService.getById(id))
                .build();
    }

    @PutMapping("/{id}")
    public BaseResponse<ShowtimeResponse> updateById(
            @PathVariable String id,
            @Valid @RequestBody ShowtimeRequest request)
    {
        return BaseResponse.<ShowtimeResponse>builder()
                .data(showtimeService.updateById(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> delete(@PathVariable String id) {
        showtimeService.delete(id);
        return BaseResponse.<Void>builder()
                .message("Delete successfully!")
                .build();
    }

    @GetMapping("/findAll/by")
    public BaseResponse<List<ShowtimeResponse>> getShowtimesByDateAndCinema (
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam("cinemaId") String cinemaId) {
        log.info("date {}", date);
        log.info("id {}", cinemaId);
        return BaseResponse.<List<ShowtimeResponse>>builder()
                .data(showtimeService.getShowTimesByCinemaIdAndDate(date, cinemaId))
                .build();
    }

    @GetMapping("/findAll/byMovie")
    public BaseResponse<List<ShowtimeResponse>> getShowtimesByMovieIdAndDateAndCinema (
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam("cinemaId") String cinemaId,
            @RequestParam("movieId") String movieId
    ) {
        log.info("date {}", date);
        log.info("id {}", cinemaId);
        return BaseResponse.<List<ShowtimeResponse>>builder()
                .data(showtimeService.getShowTimesByMovieIdAndCinemaIdAndDate(date, cinemaId, movieId))
                .build();
    }
}
