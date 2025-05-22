package com.cdw_ticket.cinema_service.mapper;

import com.cdw_ticket.cinema_service.dto.request.CinemaBrandRequest;
import com.cdw_ticket.cinema_service.dto.response.CinemaBrandResponse;
import com.cdw_ticket.cinema_service.entity.CinemaBrand;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CinemaBrandMapper {
    CinemaBrand toCinemaBrand(CinemaBrandRequest request);
    CinemaBrandResponse toCinemaBrandResponse(CinemaBrand brand);
    void update(@MappingTarget CinemaBrand brand, CinemaBrandRequest request);
}
