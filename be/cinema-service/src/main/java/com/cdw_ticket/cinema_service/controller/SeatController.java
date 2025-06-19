package com.cdw_ticket.cinema_service.controller;

import com.cdw_ticket.cinema_service.dto.request.SeatLayoutRequest;
import com.cdw_ticket.cinema_service.dto.request.SeatRequest;
import com.cdw_ticket.cinema_service.dto.response.BaseResponse;
import com.cdw_ticket.cinema_service.dto.response.SeatLayoutResponse;
import com.cdw_ticket.cinema_service.dto.response.SeatResponse;
import com.cdw_ticket.cinema_service.entity.Seat;
import com.cdw_ticket.cinema_service.enums.SeatStatus;
import com.cdw_ticket.cinema_service.service.SeatService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SeatController {
    SeatService seatService;

    @PostMapping("/rooms/{roomId}/seats/layout")
    public BaseResponse<SeatLayoutResponse> createSeatLayout(@PathVariable String roomId,
                                                             @Valid @RequestBody SeatLayoutRequest request) {
        return BaseResponse.<SeatLayoutResponse>builder()
                .data(seatService.createSeatLayout(roomId, request))
                .build();
    }

    @GetMapping("/rooms/{roomId}/seats/layout")
    public BaseResponse<SeatLayoutResponse> getSeatLayout(@PathVariable String roomId) {
        return BaseResponse.<SeatLayoutResponse>builder()
                .data(seatService.getSeatLayoutByRoomId(roomId))
                .build();
    }

    @PutMapping("/rooms/{roomId}/seats/layout")
    public BaseResponse<SeatLayoutResponse> updateSeatLayout(@PathVariable String roomId,
                                                             @Valid @RequestBody SeatLayoutRequest request) {
        return BaseResponse.<SeatLayoutResponse>builder()
                .data(seatService.updateSeatLayoutByRoomId(roomId, request))
                .build();
    }

    @PutMapping("/seats/{id}")
    public BaseResponse<SeatResponse> updateSeat(@PathVariable String id,
                                                 @Valid @RequestBody SeatRequest request) {
        return BaseResponse.<SeatResponse>builder()
                .data(seatService.updateSeat(id, request))
                .build();
    }

    @PostMapping("/seats")
    public BaseResponse<List<SeatResponse>> getAvailableSeats(@RequestBody List<String> seatIds) {
        return BaseResponse.<List<SeatResponse>>builder()
                .data(seatService.getAvailableSeatsByIds(seatIds))
                .build();
    }

    @GetMapping("/seats/{id}/info")
    public BaseResponse<SeatResponse> getInfo(@PathVariable String id) {
        return BaseResponse.<SeatResponse>builder()
                .data(seatService.getInfo(id))
                .build();
    }

    @PostMapping("/seats/updateStatus")
    public BaseResponse<Void> updateStatusByIds(@RequestBody List<String> ids,
                                                @RequestParam SeatStatus status) {
        seatService.updateStatusBySeatIds(ids, status);
        return BaseResponse.<Void>builder()
                .message("Update status: " + status)
                .build();
    }
}
