package com.cdw_ticket.cinema_service.service;

import com.cdw_ticket.cinema_service.dto.request.SeatLayoutRequest;
import com.cdw_ticket.cinema_service.dto.request.SeatRequest;
import com.cdw_ticket.cinema_service.dto.response.SeatLayoutResponse;
import com.cdw_ticket.cinema_service.dto.response.SeatResponse;
import com.cdw_ticket.cinema_service.entity.Seat;
import com.cdw_ticket.cinema_service.enums.SeatStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SeatService {
    SeatLayoutResponse createSeatLayout(String roomId, SeatLayoutRequest request);
    SeatLayoutResponse getSeatLayoutByRoomId(String roomId);
    SeatLayoutResponse updateSeatLayoutByRoomId(String roomId, SeatLayoutRequest request);
    void deleteAllSeatByRoomId(String roomId);
    SeatResponse updateSeat(String id, SeatRequest seatRequest);
    SeatResponse getInfo(String id);
    List<SeatResponse> getAvailableSeatsByIds(List<String> seatIds);
    void updateStatusBySeatIds(List<String> ids, SeatStatus status);
}
