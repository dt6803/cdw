package com.cdw_ticket.booking_service.mapper;

import com.cdw_ticket.booking_service.dto.request.BookingUserRequest;
import com.cdw_ticket.booking_service.dto.response.BookingResponse;
import com.cdw_ticket.booking_service.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    @Mapping(target = "totalPrice", ignore = true)
    Booking toBooking(BookingUserRequest bookingUserRequest);
    BookingResponse toBookingResponse(Booking booking);
}
