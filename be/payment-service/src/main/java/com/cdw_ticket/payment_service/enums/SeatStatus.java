package com.cdw_ticket.payment_service.enums;

public enum SeatStatus {
    AVAILABLE,      // Ghế trống, có thể đặt
    BOOKED,         // Ghế đã thanh toán
    OCCUPIED,
    RESERVED,       // Ghế đang giữ chỗ (cho VIP, ưu tiên)
    UNAVAILABLE,    // Ghế hỏng/tạm ngừng sử dụng
    PENDING_PAYMENT // Ghế đang chờ thanh toán (giữ tạm)
}
