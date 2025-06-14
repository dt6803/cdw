package com.cdw_ticket.cinema_service.service.impl;

import com.cdw_ticket.cinema_service.dto.request.SeatLayoutRequest;
import com.cdw_ticket.cinema_service.dto.request.SeatRequest;
import com.cdw_ticket.cinema_service.dto.response.SeatLayoutResponse;
import com.cdw_ticket.cinema_service.dto.response.SeatResponse;
import com.cdw_ticket.cinema_service.entity.CinemaRoom;
import com.cdw_ticket.cinema_service.entity.Seat;
import com.cdw_ticket.cinema_service.enums.SeatStatus;
import com.cdw_ticket.cinema_service.exception.AppException;
import com.cdw_ticket.cinema_service.exception.ErrorCode;
import com.cdw_ticket.cinema_service.mapper.SeatMapper;
import com.cdw_ticket.cinema_service.repository.SeatRepository;
import com.cdw_ticket.cinema_service.service.CinemaRoomService;
import com.cdw_ticket.cinema_service.service.SeatService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SeatServiceImpl implements SeatService {
    SeatRepository seatRepository;
    SeatMapper seatMapper;
    CinemaRoomService roomService;

    @Override
    public SeatLayoutResponse createSeatLayout(SeatLayoutRequest request) {
        CinemaRoom room = roomService.getRoomById(request.getRoomId());
        if (room.getSeats().size() + request.getSeats().size() > room.getCapacity())
            throw new AppException(ErrorCode.ROOM_CAPACITY_EXCEED);
        List<Seat> seats = new ArrayList<>();
        for (SeatRequest seatRequest: request.getSeats()) {
            Seat seat = seatMapper.toSeat(seatRequest);
            seat.setRoom(room);
            seats.add(seat);
        }

        seatRepository.saveAll(seats);

        return SeatLayoutResponse.builder()
                .roomId(request.getRoomId())
                .total(seats.size())
                .seats(seats.stream()
                        .map(seatMapper::toSeatResponse)
                        .toList())
                .build();
    }

    @Override
    public SeatLayoutResponse getSeatLayoutByRoomId(String roomId) {
        List<Seat> seats = seatRepository.getAllSeatsByRoomId(roomId);
        return SeatLayoutResponse.builder()
                .roomId(roomId)
                .total(seats.size())
                .seats(seats.stream()
                        .map(seatMapper::toSeatResponse)
                        .toList())
                .build();
    }

    @Transactional
    @Override
    public SeatLayoutResponse updateSeatLayoutByRoomId(String roomId, SeatLayoutRequest request) {
        deleteAllSeatByRoomId(roomId);
        return createSeatLayout(request);
    }

    @Override
    public void deleteAllSeatByRoomId(String roomId) {
        seatRepository.deleteAllByRoomId(roomId);
    }

    @Override
    public SeatResponse updateSeat(String id, SeatRequest request) {
        var seat = seatRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SEAT_NOT_EXISTED));
        seatMapper.updateSeat(seat, request);
        seatRepository.save(seat);
        return seatMapper.toSeatResponse(seat);
    }

    @Override
    public SeatResponse getInfo(String id) {
        var seat = seatRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SEAT_NOT_EXISTED));
        return seatMapper.toSeatResponse(seat);
    }

    @Override
    public List<SeatResponse> getAvailableSeatsByIds(List<String> seatIds) {
        return seatRepository.findByIdInAndStatus(seatIds, SeatStatus.AVAILABLE)
                .stream()
                .map(seatMapper::toSeatResponse)
                .toList();
    }

    @Override
    public void updateStatusBySeatIds(List<String> ids, SeatStatus status) {
        int updated = seatRepository.updateStatusBySeatIds(ids, status);
        if (updated != ids.size()) {
            throw new AppException(ErrorCode.SEAT_UPDATE_FAILED);
        }
    }
}
