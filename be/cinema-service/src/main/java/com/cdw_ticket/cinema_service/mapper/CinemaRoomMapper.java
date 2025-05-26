package com.cdw_ticket.cinema_service.mapper;

import com.cdw_ticket.cinema_service.dto.request.CinemaRoomRequest;
import com.cdw_ticket.cinema_service.dto.response.CinemaRoomResponse;
import com.cdw_ticket.cinema_service.entity.CinemaRoom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CinemaRoomMapper {
    @Mapping(target = "cinema", ignore = true)
    CinemaRoom toCinemaRoom(CinemaRoomRequest request);
    CinemaRoomResponse toCinemaRoomResponse(CinemaRoom room);
    @Mapping(target = "cinema", ignore = true)
    void updateInf(@MappingTarget CinemaRoom room, CinemaRoomRequest request);
}
