package com.cdw_ticket.booking_service.service.impl;

import com.cdw_ticket.booking_service.client.CinemaClient;
import com.cdw_ticket.booking_service.client.MovieClient;
import com.cdw_ticket.booking_service.client.PaymentClient;
import com.cdw_ticket.booking_service.client.ShowtimeClient;
import com.cdw_ticket.booking_service.dto.request.BookingUserRequest;
import com.cdw_ticket.booking_service.dto.request.InitPaymentRequest;
import com.cdw_ticket.booking_service.dto.response.*;
import com.cdw_ticket.booking_service.entity.Booking;
import com.cdw_ticket.booking_service.entity.BookingSeat;
import com.cdw_ticket.booking_service.enums.BookingStatus;
import com.cdw_ticket.booking_service.enums.ShowtimeStatus;
import com.cdw_ticket.booking_service.exception.AppException;
import com.cdw_ticket.booking_service.exception.ErrorCode;
import com.cdw_ticket.booking_service.mapper.BookingMapper;
import com.cdw_ticket.booking_service.mapper.BookingSeatMapper;
import com.cdw_ticket.booking_service.repository.BookingRepository;
import com.cdw_ticket.booking_service.repository.BookingSeatRepository;
import com.cdw_ticket.booking_service.service.BookingService;
import jakarta.transaction.Transactional;
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
    BookingSeatRepository bookingSeatRepository;
    BookingMapper bookingMapper;
    BookingSeatMapper bookingSeatMapper;
    ShowtimeClient showtimeClient;
    CinemaClient cinemaClient;
    MovieClient movieClient;
    PaymentClient paymentClient;
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
        seats.forEach(seat -> {
            BookingSeat bookingSeat = BookingSeat.builder()
                    .booking(booking)
                    .seatId(seat.getId())
                    .seatCode(seat.getSeatCode())
                    .type(seat.getType())
                    .price(seat.getPrice())
                    .build();

            booking.getBookingSeats().add(bookingSeat);
        });

        bookingRepository.save(booking);

        var initPaymentRequest = InitPaymentRequest.builder()
                .userId(request.getUserId())
                .txnRef(booking.getId())
                .amount(booking.getTotalPrice().longValue())
                .build();

        var urlPayment = paymentClient.initPayment(initPaymentRequest).getData();

        MovieResponse movie = movieClient.getMovieById(showtime.getMovieId()).getData();
        var response = bookingMapper.toBookingResponse(booking);
        response.setMovieTitle(movie.getTitle());

        var cinemaInfo = cinemaClient.getSimpleInfo(showtime.getCinemaId()).getData();
        response.setCinemaName(cinemaInfo.getName());
        response.setUrlPayment(urlPayment.getVpnUrl());

        return response;
    }

    @Override
    public BookingResponse markBooked(String id) {
        var booking = bookingRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_FOUND));
        booking.setStatus(BookingStatus.COMPLETED);
        bookingRepository.save(booking);
        return bookingMapper.toBookingResponse(booking);
    }

    @Override
    public BookingResponse checkStatus(String id) {
        var booking = bookingRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_FOUND));
        return bookingMapper.toBookingResponse(booking);
    }

    @Override
    public BookingResponse getById(String id) {
        var booking = bookingRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_FOUND));
        return bookingMapper.toBookingResponse(booking);
    }

    @Override
    public List<BookingResponse> getAll() {
        return bookingRepository.findAll().stream()
                .map(bookingMapper::toBookingResponse)
                .toList();
    }

    @Override
    public List<String> getSeatsByBookingId(String id) {
        return bookingSeatRepository.findByBookingId(id).stream()
                .map(BookingSeat::getSeatId)
                .toList();
    }

    private BigDecimal calculateTotal(List<SeatResponse> seats) {
        return seats.stream()
                .map(SeatResponse::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
