export class Seat {
  id: string;
  seatCode: string;       // A1, B2, v.v.
  rowNumber: number;      // Hàng
  colNumber: number;      // Cột
  type: SeatType;
  price: number;          // Dùng number thay vì BigDecimal
  status: SeatStatus;
  room: string;
}

export enum SeatType {
  NORMAL = 'NORMAL',
  VIP = 'VIP',
  COUPLE = 'COUPLE',
}

export enum SeatStatus {
  AVAILABLE = 'AVAILABLE',      // Ghế trống, có thể đặt
  BOOKED = 'BOOKED',         // Ghế đã thanh toán
  OCCUPIED = 'OCCUPIED',         // Ghế đã được đặt nhưng chưa thanh toán (có thể là giữ chỗ),
  RESERVED = 'RESERVED',       // Ghế đang giữ chỗ (cho VIP, ưu tiên)
  UNAVAILABLE = 'UNAVAILABLE',    // Ghế hỏng/tạm ngừng sử dụng
  PENDING_PAYMENT = 'PENDING_PAYMENT' // Ghế đang chờ thanh toán (giữ tạm)
}
