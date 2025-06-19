package com.cdw_ticket.payment_service.service.impl;

import com.cdw_ticket.payment_service.client.BookingClient;
import com.cdw_ticket.payment_service.client.CinemaClient;
import com.cdw_ticket.payment_service.client.NotificationClient;
import com.cdw_ticket.payment_service.client.ProfileClient;
import com.cdw_ticket.payment_service.constant.VNPayParams;
import com.cdw_ticket.payment_service.constant.VnpIpnResponseConst;
import com.cdw_ticket.payment_service.dto.request.SendMailRequest;
import com.cdw_ticket.payment_service.dto.response.IpnResponse;
import com.cdw_ticket.payment_service.entity.Recipient;
import com.cdw_ticket.payment_service.enums.SeatStatus;
import com.cdw_ticket.payment_service.exception.AppException;
import com.cdw_ticket.payment_service.service.IpnHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class VNPayIpnHandler implements IpnHandler {
    VNPayService vnPayService;
    BookingClient bookingClient;
    CinemaClient cinemaClient;
    NotificationClient notificationClient;
    ProfileClient profileClient;
    @Override
    public IpnResponse process(Map<String, String> params) {
        if (!vnPayService.verifyIpn(params)) {
            return VnpIpnResponseConst.SIGNATURE_FAILED;
        }

        IpnResponse response;
        var txnRef = params.get(VNPayParams.TXN_REF);
        try {

            var bookingId = txnRef.trim();
            markBooked(bookingId);




            response = VnpIpnResponseConst.SUCCESS;
        }
        catch (AppException e) {
            switch (e.getErrorCode()) {
                case BOOKING_NOT_FOUND -> response = VnpIpnResponseConst.ORDER_NOT_FOUND;
                default -> response = VnpIpnResponseConst.UNKNOWN_ERROR;
            }
        }
        catch (Exception e) {
            response = VnpIpnResponseConst.UNKNOWN_ERROR;
        }
        return response;
    }

    private void markBooked(String bookingId) {
        bookingClient.markBooked(bookingId);
        List<String> seats = bookingClient.getSeatByBookingId(bookingId).getData();
        cinemaClient.updateSeatsStatusByIds(seats, SeatStatus.BOOKED);
    }

//    private void sendEmailConfirm(String bookingId) {
//        var booking = bookingClient.getById(bookingId).getData();
//        var userId = booking.getUserId();
//        var profile = profileClient.getByUserId(userId).getData();
//        var email = profile.getEmail();
//        var fullname = profile.getFullName();
//
//        Map<String, Object> emailData = new HashMap<>();
//        emailData.put("movieTitle", booking.getMovieTitle());
//        emailData.put("showtime", booking.getShowtime());
//        emailData.put("createdAt", booking.getCreatedAt());
//        emailData.put("totalPrice", booking.getTotalPrice());
//        emailData.put("fullname", fullname);
//        var recipient = new Recipient(fullname, email);
//
//        notificationClient.sendMail(
//                SendMailRequest.builder()
//                        .to(recipient)
//                        .subject("ĐƠN VÉ T CINEMA")
//                        .htmlContent("booking-confirmation")
//                        .data(emailData)
//                        .build()
//        );
//    }
}
