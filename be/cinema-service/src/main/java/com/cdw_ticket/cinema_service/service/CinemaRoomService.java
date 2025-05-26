package com.cdw_ticket.cinema_service.service;

import com.cdw_ticket.cinema_service.dto.request.CinemaRoomRequest;
import com.cdw_ticket.cinema_service.dto.response.CinemaRoomResponse;
import com.cdw_ticket.cinema_service.entity.CinemaRoom;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CinemaRoomService {
    CinemaRoomResponse create(CinemaRoomRequest request);
    CinemaRoomResponse update(String id, CinemaRoomRequest request);
    CinemaRoomResponse getInfoById(String id);
    void delete(String id);
    List<CinemaRoomResponse> getRoomsByCinemaId(String cinemaId);
    CinemaRoom getRoomById(String id);
}
