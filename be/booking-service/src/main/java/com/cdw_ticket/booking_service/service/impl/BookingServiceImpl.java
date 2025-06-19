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
import com.cdw_ticket.booking_service.service.QrService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    QrService qrService;
    @Override
    public BookingResponse create(BookingUserRequest request) {
        ShowtimeResponse showtime = showtimeClient.getById(request.getShowtimeId()).getData();

        if (showtime.getStatus() != ShowtimeStatus.AVAILABLE)
            throw new AppException(ErrorCode.SHOWTIME_NOT_AVAILABLE);

        List<SeatResponse> seats = cinemaClient.getAvailableSeatByIds(request.getSeatIds()).getData();
        if (seats.size() != request.getSeatIds().size())
            throw new AppException(ErrorCode.INVALID_SEAT);

        Booking booking = bookingMapper.toBooking(request);
        booking.setTotalPrice(request.getTotal());
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
                .amount(request.getTotal().longValue())
                .build();

        var urlPayment = paymentClient.initPayment(initPaymentRequest).getData();

        MovieResponse movie = movieClient.getMovieById(showtime.getMovieId()).getData();
        var response = bookingMapper.toBookingResponse(booking);
        response.setMovieTitle(movie.getTitle());

        var cinemaInfo = cinemaClient.getSimpleInfo(showtime.getCinemaId()).getData();
        response.setCinemaName(cinemaInfo.getName());
        response.setUrlPayment(urlPayment.getVpnUrl());

        response.setShowtime(showtime.getStartTime());
        try {
            var qrCode = qrService.generateQRCodeImage(booking.getId(), 250, 250);
            String qrCodeBase64 = qrService.toBase64(qrCode);
            response.setQrCodeBase64(qrCodeBase64);
            log.info("qr code: {}", qrCode);
        } catch (Exception e) {
            log.error("QR code issue: {}", e.getMessage());
        }

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
        var response = bookingMapper.toBookingResponse(booking);
        var showtime = showtimeClient.getById(booking.getShowtimeId()).getData();
        var movieTitle = movieClient.getMovieById(showtime.getMovieId()).getData().getTitle();
        var cinema = cinemaClient.getSimpleInfo(showtime.getCinemaId()).getData();
        response.setMovieTitle(movieTitle);
        response.setShowtime(showtime.getStartTime());
        response.setCinemaName(cinema.getName());

        try {
            var qrCode = qrService.generateQRCodeImage(booking.getId(), 250, 250);
            String qrCodeBase64 = qrService.toBase64(qrCode);
            response.setQrCodeBase64(qrCodeBase64);
        } catch (Exception e) {
            log.error("QR code issue: {}", e.getMessage());
        }
        return response;
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

    @Override
    public List<BookingResponse> getAllByUserId(String userId) {
        List<Booking> list = bookingRepository.findAllByUserId(userId);
        List<BookingResponse> res = new ArrayList<>();
        for (Booking b: list) {
            var aBooking = bookingMapper.toBookingResponse(b);
            var showtime = showtimeClient.getById(b.getShowtimeId()).getData();
            var movieTitle = movieClient.getMovieById(showtime.getMovieId()).getData().getTitle();
            var cinema = cinemaClient.getSimpleInfo(showtime.getCinemaId()).getData();
            aBooking.setMovieTitle(movieTitle);
            aBooking.setShowtime(showtime.getStartTime());
            aBooking.setCinemaName(cinema.getName());
            res.add(aBooking);
        }
        return res;
    }


}
