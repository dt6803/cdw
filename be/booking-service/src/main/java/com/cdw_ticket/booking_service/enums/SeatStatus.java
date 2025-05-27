package com.cdw_ticket.booking_service.enums;

public enum SeatStatus {
    AVAILABLE,      // Ghế trống, có thể đặt
    BOOKED,         // Ghế đã được đặt (chưa thanh toán)
    OCCUPIED,       // Ghế đã có người ngồi (đã check-in)
    RESERVED,       // Ghế đang giữ chỗ (cho VIP, ưu tiên)
    UNAVAILABLE,    // Ghế hỏng/tạm ngừng sử dụng
    PENDING_PAYMENT // Ghế đang chờ thanh toán (giữ tạm)
}
