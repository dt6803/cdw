package com.cdw_ticket.cinema_service.mapper;

import com.cdw_ticket.cinema_service.dto.request.SeatRequest;
import com.cdw_ticket.cinema_service.dto.response.SeatResponse;
import com.cdw_ticket.cinema_service.entity.Seat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SeatMapper {
    @Mapping(target = "room", ignore = true)
    Seat toSeat(SeatRequest request);
    SeatResponse toSeatResponse(Seat seat);
    void updateSeat(@MappingTarget Seat seat, SeatRequest request);
}
