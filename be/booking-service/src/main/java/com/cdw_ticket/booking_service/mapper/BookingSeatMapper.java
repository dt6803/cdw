package com.cdw_ticket.booking_service.mapper;

import com.cdw_ticket.booking_service.dto.response.BookingSeatResponse;
import com.cdw_ticket.booking_service.dto.response.SeatResponse;
import com.cdw_ticket.booking_service.entity.BookingSeat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingSeatMapper {
    @Mapping(target = "booking", ignore = true)
    BookingSeat toBookingSeat(SeatResponse seat);

    BookingSeatResponse toBookingSeat(BookingSeat bookingSeat);
}
