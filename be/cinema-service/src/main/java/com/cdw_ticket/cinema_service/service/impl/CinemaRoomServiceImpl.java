package com.cdw_ticket.cinema_service.service.impl;

import com.cdw_ticket.cinema_service.dto.request.CinemaRoomRequest;
import com.cdw_ticket.cinema_service.dto.response.CinemaRoomResponse;
import com.cdw_ticket.cinema_service.entity.CinemaRoom;
import com.cdw_ticket.cinema_service.exception.AppException;
import com.cdw_ticket.cinema_service.exception.ErrorCode;
import com.cdw_ticket.cinema_service.mapper.CinemaRoomMapper;
import com.cdw_ticket.cinema_service.repository.CinemaRoomRepository;
import com.cdw_ticket.cinema_service.service.CinemaRoomService;
import com.cdw_ticket.cinema_service.service.CinemaService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CinemaRoomServiceImpl implements CinemaRoomService {
    CinemaRoomRepository cinemaRoomRepository;
    CinemaRoomMapper cinemaRoomMapper;
    CinemaService cinemaService;
    @Override
    public CinemaRoomResponse create(CinemaRoomRequest request) {
        var room = cinemaRoomMapper.toCinemaRoom(request);
        var cinema = cinemaService.getCinemaById(request.getCinemaId());
        room.setCinema(cinema);
        cinemaRoomRepository.save(room);
        return cinemaRoomMapper.toCinemaRoomResponse(room);
    }

    @Override
    public CinemaRoomResponse update(String id, CinemaRoomRequest request) {
        var room = cinemaRoomRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_EXISTED));
        cinemaRoomMapper.updateInf(room, request);
        cinemaRoomRepository.save(room);
        return cinemaRoomMapper.toCinemaRoomResponse(room);
    }

    @Override
    public CinemaRoomResponse getInfoById(String id) {
        var room = cinemaRoomRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_EXISTED));
        var response = cinemaRoomMapper.toCinemaRoomResponse(room);
        response.setCinemaId(room.getCinema().getId());
        return response;
    }

    @Override
    public void delete(String id) {
        cinemaRoomRepository.deleteById(id);
    }

    @Override
    public List<CinemaRoomResponse> getRoomsByCinemaId(String cinemaId) {
        return cinemaRoomRepository.getRoomsByCinemaId(cinemaId)
                .stream()
                .map(cinemaRoomMapper::toCinemaRoomResponse)
                .toList();
    }

    @Override
    public CinemaRoom getRoomById(String id) {
        return cinemaRoomRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_EXISTED));
    }
}
