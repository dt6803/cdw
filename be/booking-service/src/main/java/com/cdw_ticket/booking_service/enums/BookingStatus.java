package com.cdw_ticket.booking_service.enums;

public enum BookingStatus {
    PENDING,    // Chờ thanh toán
    CONFIRMED,  // Thanh toán thành công, vé được xác nhận
    CANCELLED,  // Đơn đặt vé bị hủy
    EXPIRED,     // Hết thời gian thanh toán
    COMPLETED,  //Hoàn thành
}
