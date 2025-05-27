package com.cdw_ticket.booking_service.service.impl;

import com.cdw_ticket.booking_service.client.CinemaClient;
import com.cdw_ticket.booking_service.client.MovieClient;
import com.cdw_ticket.booking_service.client.ShowtimeClient;
import com.cdw_ticket.booking_service.dto.request.BookingUserRequest;
import com.cdw_ticket.booking_service.dto.response.BookingResponse;
import com.cdw_ticket.booking_service.dto.response.MovieResponse;
import com.cdw_ticket.booking_service.dto.response.SeatResponse;
import com.cdw_ticket.booking_service.dto.response.ShowtimeResponse;
import com.cdw_ticket.booking_service.entity.Booking;
import com.cdw_ticket.booking_service.entity.BookingSeat;
import com.cdw_ticket.booking_service.enums.BookingStatus;
import com.cdw_ticket.booking_service.enums.ShowtimeStatus;
import com.cdw_ticket.booking_service.exception.AppException;
import com.cdw_ticket.booking_service.exception.ErrorCode;
import com.cdw_ticket.booking_service.mapper.BookingMapper;
import com.cdw_ticket.booking_service.mapper.BookingSeatMapper;
import com.cdw_ticket.booking_service.repository.BookingRepository;
import com.cdw_ticket.booking_service.service.BookingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookingServiceImpl implements BookingService {
    BookingRepository bookingRepository;
    BookingMapper bookingMapper;
    BookingSeatMapper bookingSeatMapper;
    ShowtimeClient showtimeClient;
    CinemaClient cinemaClient;
    MovieClient movieClient;
    @Override
    public BookingResponse create(BookingUserRequest request) {
        ShowtimeResponse showtime = showtimeClient.getById(request.getShowtimeId()).getData();

        if (showtime.getStatus() != ShowtimeStatus.AVAILABLE)
            throw new AppException(ErrorCode.SHOWTIME_NOT_AVAILABLE);

        List<SeatResponse> seats = cinemaClient.getAvailableSeatByIds(request.getSeatIds()).getData();
        if (seats.size() != request.getSeatIds().size())
            throw new AppException(ErrorCode.INVALID_SEAT);

        Booking booking = bookingMapper.toBooking(request);
        booking.setTotalPrice(calculateTotal(seats));
        booking.setStatus(BookingStatus.CONFIRMED);

        seats.forEach(seat ->{
            BookingSeat bookingSeat = bookingSeatMapper.toBookingSeat(seat);
            bookingSeat.setBooking(booking);
            booking.getBookingSeats().add(bookingSeat);
        });

        bookingRepository.save(booking);

        MovieResponse movie = movieClient.getMovieById(showtime.getMovieId()).getData();
        var response = bookingMapper.toBookingResponse(booking);
        response.setMovieTitle(movie.getTitle());

        return response;
    }

    private BigDecimal calculateTotal(List<SeatResponse> seats) {
        return seats.stream()
                .map(SeatResponse::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
