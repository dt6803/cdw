package com.cdw_ticket.cinema_service.mapper;

import com.cdw_ticket.cinema_service.dto.request.ComboRequest;
import com.cdw_ticket.cinema_service.dto.response.ComboResponse;
import com.cdw_ticket.cinema_service.entity.Combo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ComboMapper {
    Combo toCombo(ComboRequest request);
    ComboResponse toComoResponse(Combo combo);
}
