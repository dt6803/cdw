package com.cdw_ticket.cinema_service.controller;

import com.cdw_ticket.cinema_service.dto.request.CinemaRoomRequest;
import com.cdw_ticket.cinema_service.dto.response.BaseResponse;
import com.cdw_ticket.cinema_service.dto.response.CinemaRoomResponse;
import com.cdw_ticket.cinema_service.service.CinemaRoomService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CinemaRoomController {
    CinemaRoomService roomService;

    @PostMapping("/rooms")
    public BaseResponse<CinemaRoomResponse> create(@Valid @RequestBody CinemaRoomRequest request) {
        return BaseResponse.<CinemaRoomResponse>builder()
                .data(roomService.create(request))
                .build();
    }

    @PutMapping("/rooms/{id}")
    public BaseResponse<CinemaRoomResponse> updateInfo(@PathVariable String id,
                                                       @Valid @RequestBody CinemaRoomRequest request
    ) {
        return BaseResponse.<CinemaRoomResponse>builder()
                .data(roomService.update(id, request))
                .build();
    }

    @GetMapping("/rooms/{id}")
    public BaseResponse<CinemaRoomResponse> getInfo(@PathVariable String id) {
        return BaseResponse.<CinemaRoomResponse>builder()
                .data(roomService.getInfoById(id))
                .build();
    }

    @DeleteMapping("rooms/{id}")
    public BaseResponse<Void> delete(@PathVariable String id) {
        roomService.delete(id);
        return BaseResponse.<Void>builder()
                .message("Delete successfully!")
                .build();
    }

    @GetMapping("/cinemas/{cinemaId}/rooms")
    public BaseResponse<List<CinemaRoomResponse>> getRoomsByCinemaId(@PathVariable String cinemaId) {
        return BaseResponse.<List<CinemaRoomResponse>>builder()
                .data(roomService.getRoomsByCinemaId(cinemaId))
                .build();
    }

}
