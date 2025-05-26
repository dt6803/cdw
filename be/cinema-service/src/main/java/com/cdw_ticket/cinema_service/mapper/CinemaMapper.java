package com.cdw_ticket.cinema_service.mapper;

import com.cdw_ticket.cinema_service.dto.request.CinemaRequest;
import com.cdw_ticket.cinema_service.dto.response.CinemaDetailResponse;
import com.cdw_ticket.cinema_service.dto.response.CinemaSimpleResponse;
import com.cdw_ticket.cinema_service.entity.Cinema;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CinemaMapper {
    @Mapping(target = "brand", ignore = true)
    Cinema toCinema(CinemaRequest request);
    CinemaSimpleResponse toCinemaSimpleResponse(Cinema cinema);
    CinemaDetailResponse toCinemaDetailResponse(Cinema cinema);
    @Mapping(target = "brand", ignore = true)
    void updateInfo(@MappingTarget Cinema cinema, CinemaRequest request);

}
