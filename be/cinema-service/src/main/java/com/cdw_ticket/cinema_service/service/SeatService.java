package com.cdw_ticket.cinema_service.service;

import com.cdw_ticket.cinema_service.dto.request.SeatLayoutRequest;
import com.cdw_ticket.cinema_service.dto.request.SeatRequest;
import com.cdw_ticket.cinema_service.dto.response.SeatLayoutResponse;
import com.cdw_ticket.cinema_service.dto.response.SeatResponse;
import org.springframework.stereotype.Service;

@Service
public interface SeatService {
    SeatLayoutResponse createSeatLayout(SeatLayoutRequest request);
    SeatLayoutResponse getSeatLayoutByRoomId(String roomId);
    SeatLayoutResponse updateSeatLayoutByRoomId(String roomId, SeatLayoutRequest request);
    void deleteAllSeatByRoomId(String roomId);
    SeatResponse updateSeat(String id, SeatRequest seatRequest);
}
