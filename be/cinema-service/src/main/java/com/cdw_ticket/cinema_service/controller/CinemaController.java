package com.cdw_ticket.cinema_service.controller;

import com.cdw_ticket.cinema_service.dto.request.CinemaRequest;
import com.cdw_ticket.cinema_service.dto.response.BaseResponse;
import com.cdw_ticket.cinema_service.dto.response.CinemaDetailResponse;
import com.cdw_ticket.cinema_service.dto.response.CinemaSimpleResponse;
import com.cdw_ticket.cinema_service.service.CinemaService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/cinemas")
public class CinemaController {
    CinemaService cinemaService;

    @PostMapping
    public BaseResponse<CinemaSimpleResponse> create(@Valid @RequestBody CinemaRequest request) {
        return BaseResponse.<CinemaSimpleResponse>builder()
                .data(cinemaService.create(request))
                .build();
    }

    @GetMapping
    public BaseResponse<List<CinemaSimpleResponse>> getAll() {
        return BaseResponse.<List<CinemaSimpleResponse>>builder()
                .data(cinemaService.getAll())
                .build();
    }

    @GetMapping("/info/{id}")
    public BaseResponse<CinemaSimpleResponse> getSimpleInfo(@PathVariable String id) {
        return BaseResponse.<CinemaSimpleResponse>builder()
                .data(cinemaService.getSimpleInfo(id))
                .build();
    }

    @GetMapping("/detail/{id}")
    public BaseResponse<CinemaDetailResponse> getDetailInfo(@PathVariable String id) {
        return BaseResponse.<CinemaDetailResponse>builder()
                .data(cinemaService.getDetailInfo(id))
                .build();
    }

    @PutMapping("/info/{id}")
    public BaseResponse<CinemaSimpleResponse> updateInfo(@PathVariable String id,
                                                         @Valid @RequestBody CinemaRequest request
    ) {
        return BaseResponse.<CinemaSimpleResponse>builder()
                .data(cinemaService.updateInfo(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> delete(@PathVariable String id) {
        cinemaService.delete(id);
        return BaseResponse.<Void>builder()
                .message("Delete successfully!")
                .build();
    }


}
